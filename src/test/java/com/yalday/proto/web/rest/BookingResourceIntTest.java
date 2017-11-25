package com.yalday.proto.web.rest;

import com.yalday.proto.YaldayProtoApp;

import com.yalday.proto.domain.Booking;
import com.yalday.proto.repository.BookingRepository;
import com.yalday.proto.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.yalday.proto.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the BookingResource REST controller.
 *
 * @see BookingResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = YaldayProtoApp.class)
public class BookingResourceIntTest {

    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_TEXT = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_START_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_START_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_END_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_END_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restBookingMockMvc;

    private Booking booking;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BookingResource bookingResource = new BookingResource(bookingRepository);
        this.restBookingMockMvc = MockMvcBuilders.standaloneSetup(bookingResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Booking createEntity() {
        Booking booking = new Booking()
            .text(DEFAULT_TEXT)
            .startDate(DEFAULT_START_TIME)
            .endDate(DEFAULT_END_TIME);
        return booking;
    }

    @Before
    public void initTest() {
        bookingRepository.deleteAll();
        booking = createEntity();
    }

    @Test
    public void createBooking() throws Exception {
        int databaseSizeBeforeCreate = bookingRepository.findAll().size();

        // Create the Booking
        restBookingMockMvc.perform(post("/api/bookings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(booking)))
            .andExpect(status().isCreated());

        // Validate the Booking in the database
        List<Booking> bookingList = bookingRepository.findAll();
        assertThat(bookingList).hasSize(databaseSizeBeforeCreate + 1);
        Booking testBooking = bookingList.get(bookingList.size() - 1);
        assertThat(testBooking.getText()).isEqualTo(DEFAULT_TEXT);
        assertThat(testBooking.getStartDate()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testBooking.getEndDate()).isEqualTo(DEFAULT_END_TIME);
    }

    @Test
    public void createBookingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bookingRepository.findAll().size();

        // Create the Booking with an existing ID
        booking.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restBookingMockMvc.perform(post("/api/bookings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(booking)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Booking> bookingList = bookingRepository.findAll();
        assertThat(bookingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void getAllBookings() throws Exception {
        // Initialize the database
        bookingRepository.save(booking);

        // Get all the bookingList
        restBookingMockMvc.perform(get("/api/bookings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(booking.getId())))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(sameInstant(DEFAULT_START_TIME))))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(sameInstant(DEFAULT_END_TIME))));
    }

    @Test
    public void getBooking() throws Exception {
        // Initialize the database
        bookingRepository.save(booking);

        // Get the booking
        restBookingMockMvc.perform(get("/api/bookings/{id}", booking.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(booking.getId()))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT.toString()))
            .andExpect(jsonPath("$.startDate").value(sameInstant(DEFAULT_START_TIME)))
            .andExpect(jsonPath("$.endDate").value(sameInstant(DEFAULT_END_TIME)));
    }

    @Test
    public void getNonExistingBooking() throws Exception {
        // Get the booking
        restBookingMockMvc.perform(get("/api/bookings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateBooking() throws Exception {
        // Initialize the database
        bookingRepository.save(booking);
        int databaseSizeBeforeUpdate = bookingRepository.findAll().size();

        // Update the booking
        Booking updatedBooking = bookingRepository.findOne(booking.getId());
        updatedBooking
            .text(UPDATED_TEXT)
            .startDate(UPDATED_START_TIME)
            .endDate(UPDATED_END_TIME);

        restBookingMockMvc.perform(put("/api/bookings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBooking)))
            .andExpect(status().isOk());

        // Validate the Booking in the database
        List<Booking> bookingList = bookingRepository.findAll();
        assertThat(bookingList).hasSize(databaseSizeBeforeUpdate);
        Booking testBooking = bookingList.get(bookingList.size() - 1);
        assertThat(testBooking.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testBooking.getStartDate()).isEqualTo(UPDATED_START_TIME);
        assertThat(testBooking.getEndDate()).isEqualTo(UPDATED_END_TIME);
    }

    @Test
    public void updateNonExistingBooking() throws Exception {
        int databaseSizeBeforeUpdate = bookingRepository.findAll().size();

        // Create the Booking

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBookingMockMvc.perform(put("/api/bookings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(booking)))
            .andExpect(status().isCreated());

        // Validate the Booking in the database
        List<Booking> bookingList = bookingRepository.findAll();
        assertThat(bookingList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteBooking() throws Exception {
        // Initialize the database
        bookingRepository.save(booking);
        int databaseSizeBeforeDelete = bookingRepository.findAll().size();

        // Get the booking
        restBookingMockMvc.perform(delete("/api/bookings/{id}", booking.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Booking> bookingList = bookingRepository.findAll();
        assertThat(bookingList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Booking.class);
        Booking booking1 = new Booking();
        booking1.setId("id1");
        Booking booking2 = new Booking();
        booking2.setId(booking1.getId());
        assertThat(booking1).isEqualTo(booking2);
        booking2.setId("id2");
        assertThat(booking1).isNotEqualTo(booking2);
        booking1.setId(null);
        assertThat(booking1).isNotEqualTo(booking2);
    }
}

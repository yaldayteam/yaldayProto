package com.yalday.proto.web.rest;

import com.yalday.proto.YaldayProtoApp;

import com.yalday.proto.domain.Merchant;
import com.yalday.proto.repository.MerchantRepository;
import com.yalday.proto.service.MerchantService;
import com.yalday.proto.service.dto.MerchantDTO;
import com.yalday.proto.service.mapper.MerchantMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MerchantResource REST controller.
 *
 * @see MerchantResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = YaldayProtoApp.class)
public class MerchantResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_POSTCODE = "AAAAAAAAAA";
    private static final String UPDATED_POSTCODE = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY = "BBBBBBBBBB";

    private static final String DEFAULT_BACKGROUND_COLOR = "AAAAAAAAAA";
    private static final String UPDATED_BACKGROUND_COLOR = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONENUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONENUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_USERID = "AAAAAAA";
    private static final String UPDATED_USERID = "BBBBBBB";

    @Inject
    private MerchantRepository merchantRepository;

    @Inject
    private MerchantMapper merchantMapper;

    @Inject
    private MerchantService merchantService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restMerchantMockMvc;

    private Merchant merchant;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MerchantResource merchantResource = new MerchantResource();
        ReflectionTestUtils.setField(merchantResource, "merchantService", merchantService);
        this.restMerchantMockMvc = MockMvcBuilders.standaloneSetup(merchantResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Merchant createEntity() {
        Merchant merchant = new Merchant()
                .name(DEFAULT_NAME)
                .description(DEFAULT_DESCRIPTION)
                .address(DEFAULT_ADDRESS)
                .city(DEFAULT_CITY)
                .postcode(DEFAULT_POSTCODE)
                .country(DEFAULT_COUNTRY)
                .category(DEFAULT_CATEGORY)
                .backgroundColor(DEFAULT_BACKGROUND_COLOR)
                .email(DEFAULT_EMAIL)
                .phonenumber(DEFAULT_PHONENUMBER)
                .userid(DEFAULT_USERID);
        return merchant;
    }

    @Before
    public void initTest() {
        merchantRepository.deleteAll();
        merchant = createEntity();
    }

    @Test
    public void createMerchant() throws Exception {
        int databaseSizeBeforeCreate = merchantRepository.findAll().size();

        // Create the Merchant
        MerchantDTO merchantDTO = merchantMapper.merchantToMerchantDTO(merchant);

        restMerchantMockMvc.perform(post("/api/merchants")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(merchantDTO)))
                .andExpect(status().isCreated());

        // Validate the Merchant in the database
        List<Merchant> merchants = merchantRepository.findAll();
        assertThat(merchants).hasSize(databaseSizeBeforeCreate + 1);
        Merchant testMerchant = merchants.get(merchants.size() - 1);
        assertThat(testMerchant.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMerchant.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMerchant.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testMerchant.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testMerchant.getPostcode()).isEqualTo(DEFAULT_POSTCODE);
        assertThat(testMerchant.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testMerchant.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testMerchant.getBackgroundColor()).isEqualTo(DEFAULT_BACKGROUND_COLOR);
        assertThat(testMerchant.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testMerchant.getPhonenumber()).isEqualTo(DEFAULT_PHONENUMBER);
        assertThat(testMerchant.getUserid()).isEqualTo(DEFAULT_USERID);
    }

    @Test
    public void getAllMerchants() throws Exception {
        // Initialize the database
        merchantRepository.save(merchant);

        // Get all the merchants
        restMerchantMockMvc.perform(get("/api/merchants?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(merchant.getId())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
                .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
                .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
                .andExpect(jsonPath("$.[*].postcode").value(hasItem(DEFAULT_POSTCODE.toString())))
                .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())))
                .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.toString())))
                .andExpect(jsonPath("$.[*].backgroundColor").value(hasItem(DEFAULT_BACKGROUND_COLOR.toString())))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
                .andExpect(jsonPath("$.[*].phonenumber").value(hasItem(DEFAULT_PHONENUMBER.toString())))
                .andExpect(jsonPath("$.[*].userid").value(hasItem(DEFAULT_USERID)));
    }

    @Test
    public void getMerchant() throws Exception {
        // Initialize the database
        merchantRepository.save(merchant);

        // Get the merchant
        restMerchantMockMvc.perform(get("/api/merchants/{id}", merchant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(merchant.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.postcode").value(DEFAULT_POSTCODE.toString()))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY.toString()))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY.toString()))
            .andExpect(jsonPath("$.backgroundColor").value(DEFAULT_BACKGROUND_COLOR.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.phonenumber").value(DEFAULT_PHONENUMBER.toString()))
            .andExpect(jsonPath("$.userid").value(hasItem(DEFAULT_USERID.toString())));
    }

    @Test
    public void getNonExistingMerchant() throws Exception {
        // Get the merchant
        restMerchantMockMvc.perform(get("/api/merchants/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateMerchant() throws Exception {
        // Initialize the database
        merchantRepository.save(merchant);
        int databaseSizeBeforeUpdate = merchantRepository.findAll().size();

        // Update the merchant
        Merchant updatedMerchant = merchantRepository.findOne(merchant.getId());
        updatedMerchant
                .name(UPDATED_NAME)
                .description(UPDATED_DESCRIPTION)
                .address(UPDATED_ADDRESS)
                .city(UPDATED_CITY)
                .postcode(UPDATED_POSTCODE)
                .country(UPDATED_COUNTRY)
                .category(UPDATED_CATEGORY)
                .backgroundColor(UPDATED_BACKGROUND_COLOR)
                .email(UPDATED_EMAIL)
                .phonenumber(UPDATED_PHONENUMBER)
                .userid(UPDATED_USERID);
        MerchantDTO merchantDTO = merchantMapper.merchantToMerchantDTO(updatedMerchant);

        restMerchantMockMvc.perform(put("/api/merchants")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(merchantDTO)))
                .andExpect(status().isOk());

        // Validate the Merchant in the database
        List<Merchant> merchants = merchantRepository.findAll();
        assertThat(merchants).hasSize(databaseSizeBeforeUpdate);
        Merchant testMerchant = merchants.get(merchants.size() - 1);
        assertThat(testMerchant.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMerchant.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMerchant.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testMerchant.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testMerchant.getPostcode()).isEqualTo(UPDATED_POSTCODE);
        assertThat(testMerchant.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testMerchant.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testMerchant.getBackgroundColor()).isEqualTo(UPDATED_BACKGROUND_COLOR);
        assertThat(testMerchant.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testMerchant.getPhonenumber()).isEqualTo(UPDATED_PHONENUMBER);
        assertThat(testMerchant.getUserid()).isEqualTo(UPDATED_USERID);

    }

    @Test
    public void deleteMerchant() throws Exception {
        // Initialize the database
        merchantRepository.save(merchant);
        int databaseSizeBeforeDelete = merchantRepository.findAll().size();

        // Get the merchant
        restMerchantMockMvc.perform(delete("/api/merchants/{id}", merchant.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Merchant> merchants = merchantRepository.findAll();
        assertThat(merchants).hasSize(databaseSizeBeforeDelete - 1);
    }
}

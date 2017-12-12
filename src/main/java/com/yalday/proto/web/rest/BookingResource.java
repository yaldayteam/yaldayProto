package com.yalday.proto.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.yalday.proto.domain.Booking;
import com.yalday.proto.domain.Merchant;

import com.yalday.proto.repository.BookingRepository;
import com.yalday.proto.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.yalday.proto.service.MerchantService;
import javax.inject.Inject;

import java.awt.print.Book;
import java.net.URI;
import java.net.URISyntaxException;
import com.yalday.proto.service.dto.MerchantDTO;

import java.util.List;
import java.util.Map;
import java.util.Optional;


import com.codahale.metrics.annotation.Timed;
import com.yalday.proto.domain.Appointment;
import com.yalday.proto.service.MerchantService;
import com.yalday.proto.web.rest.util.HeaderUtil;
import com.yalday.proto.service.dto.MerchantDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Arrays;


import com.yalday.proto.repository.BookingRepository;
import com.yalday.proto.domain.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * REST controller for managing Booking.
 */
@RestController
@RequestMapping("/api")
public class BookingResource {

    private final Logger log = LoggerFactory.getLogger(BookingResource.class);

    private static final String ENTITY_NAME = "booking";

    private final BookingRepository bookingRepository;

    private final MerchantService merchantService;

    public BookingResource(BookingRepository bookingRepository, MerchantService merchantService) {
        this.bookingRepository = bookingRepository;
        this.merchantService = merchantService;
    }

    /**
     * POST  /bookings : Create a new booking.
     *
     * @param booking the booking to create
     * @return the ResponseEntity with status 201 (Created) and with body the new booking, or with status 400 (Bad Request) if the booking has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */

    @PostMapping("/bookings")
    @Timed
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking) throws URISyntaxException {
        log.debug("REST request to save Booking : {}", booking);
        if (booking.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new booking cannot already have an ID")).body(null);
        }
        Booking result = bookingRepository.save(booking);

        return ResponseEntity.created(new URI("/api/bookings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * POST  /merchants/booking/{id} : Create a new booking for merchant with id.
     *
     * @param the booking to create & the merchant id to add the booking to
     * @return the ResponseEntity with status 201 (Created) and with body the new booking, or with status 400 (Bad Request) if the booking has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/merchants/booking/{id}")
    @Timed
    public ResponseEntity<Booking> createBookingForMerchant(@RequestBody Booking booking, @PathVariable String id) throws URISyntaxException {
        log.debug("REST request to save Booking to Merchant : {}", booking, id);
        if (booking.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("booking", "idexists", "A new booking cannot already have an ID")).body(null);
        }

        //Step one save booking - now result will have id
        Booking resultBooking = bookingRepository.save(booking);

        //Step two - retrieve merchant with id equal to param id
        MerchantDTO resultMerchantDTO = new MerchantDTO();
        resultMerchantDTO = merchantService.findOne(id);

        //Step three - add booking to merchant returned
        resultMerchantDTO.addBooking(resultBooking);

        //Step four - save merchant - so now merchant with nested booking has been saved and booking also saved
        merchantService.save(resultMerchantDTO);


        return ResponseEntity.created(new URI("/api/merchants/booking/" + resultBooking.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, resultBooking.getId().toString()))
            .body(resultBooking);

    }

    /**
     * GET  /merchants/bookings/{id}: Get all bookings for merchant with id.
     *
     * @param the merchant id to retrieve all bookings for
     * @return the ResponseEntity with status 200 (ok) and the list of bookings in the body
     */
    @GetMapping("/merchants/booking/{id}")
    @Timed
    public List<Booking> GetAllBookingsForMerchant(@PathVariable String id) {
        log.debug("REST request to GetAllBookingsForMerchant with id : {}", id);

        //find the merchant by the Merchant id passed in
        MerchantDTO merchantResult = merchantService.findOne(id);

        //get the bookings for that Merchant
        List<Booking> bookings = merchantResult.getBookings();

        return bookings;
    }

    /**
     * PUT  /merchants/booking/{id} : Updates an existing booking for merchant with id.
     *
     * @param the booking to update & the merchant id to update the booking for
     * @return the ResponseEntity with status 200 (OK) and with body the updated booking,
     * or with status 400(Bad Request) if the booking is not valid,
     * or with status 500 (Internal Server Error) if the booking could not be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/merchants/booking/{id}")
    @Timed
    public ResponseEntity<Booking> updatedBookingForMerchant(@RequestBody Booking booking, @PathVariable String id) throws URISyntaxException {
        log.debug("REST request to update Booking for Merchant : {}", booking, id);

        if (booking.getId() == null) {
            return createBookingForMerchant(booking, id);
        }

        //saves booking to booking collection seperately in accordance with REST
        Booking resultBooking = bookingRepository.save(booking);


        //Step two - retrieve merchant with id equal to param id
        MerchantDTO resultMerchantDTO = new MerchantDTO();
        resultMerchantDTO = merchantService.findOne(id);

        //Step three - update booking for merchant returned
        resultMerchantDTO.updateBooking(resultBooking);


        //Step four - save merchants - now merchant with updated booking has been saved and updated booking also saved
        merchantService.save(resultMerchantDTO);


        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, booking.getId().toString()))
            .body(resultBooking);
    }


    /**
     * DELETE  /merchants/booking/{id} : delete the booking for merchant with id
     *
     * @param the booking to be deleted and the merchant id to delete the booking from
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/merchants/booking/{id}")
    @Timed
    public ResponseEntity<Void> deleteBookingForMerchant(@PathVariable String id) {

        log.debug("REST request to delete Booking for Booking id : {}");

        //Step one - delete the booking from booking collection in accordance with REST
        bookingRepository.delete(id);

        //Step two - retrieve merchant with id equal to param id
        MerchantDTO resultMerchantDTO = new MerchantDTO();
        resultMerchantDTO = merchantService.findOneByBookingId(id);

        //Step three - delete booking for merchant returned
        resultMerchantDTO.deleteBooking(id);

        //Step four - save merchants - now merchant with deleted booking has been saved and deleted booking also
        merchantService.save(resultMerchantDTO);

        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

    /**
     * PUT  /bookings : Updates an existing booking.
     *
     * @param booking the booking to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated booking,
     * or with status 400 (Bad Request) if the booking is not valid,
     * or with status 500 (Internal Server Error) if the booking couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/bookings")
    @Timed
    public ResponseEntity<Booking> updateBooking(@RequestBody Booking booking) throws URISyntaxException {
        log.debug("REST request to update Booking : {}", booking);
        if (booking.getId() == null) {
            return createBooking(booking);
        }
        Booking result = bookingRepository.save(booking);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, booking.getId().toString()))
            .body(result);
    }

    /**
     * GET  /bookings : get all the bookings.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of bookings in body
     */
    @GetMapping("/bookings")
    @Timed
    public List<Booking> getAllBookings() {
        log.debug("REST request to get all Bookings");
        return bookingRepository.findAll();
    }

    /**
     * GET  /bookings/:id : get the "id" booking.
     *
     * @param id the id of the booking to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the booking, or with status 404 (Not Found)
     */
    @GetMapping("/bookings/{id}")
    @Timed
    public ResponseEntity<Booking> getBooking(@PathVariable String id) {
        log.debug("REST request to get Booking : {}", id);
        Booking booking = bookingRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(booking));
    }

    /**
     * DELETE  /bookings/:id : delete the "id" booking.
     *
     * @param id the id of the booking to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/bookings/{id}")
    @Timed
    public ResponseEntity<Void> deleteBooking(@PathVariable String id) {
        log.debug("REST request to delete Booking : {}", id);
        bookingRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}

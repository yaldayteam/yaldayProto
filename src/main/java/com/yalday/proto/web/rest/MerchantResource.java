package com.yalday.proto.web.rest;

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


import com.yalday.proto.repository.BookingRepository;
import com.yalday.proto.domain.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * REST controller for managing Merchant.
 */
@RestController
@RequestMapping("/api")
public class MerchantResource {

    private final Logger log = LoggerFactory.getLogger(MerchantResource.class);

    @Inject
    private MerchantService merchantService;



    /**
     * POST  /merchants : Create a new merchant.
     *
     * @param merchantDTO the merchantDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new merchantDTO, or with status 400 (Bad Request) if the merchant has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/merchants")
    @Timed
    public ResponseEntity<MerchantDTO> createMerchant(@RequestBody MerchantDTO merchantDTO) throws URISyntaxException {
        log.debug("REST request to save Merchant : {}", merchantDTO);
        if (merchantDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("merchant", "idexists", "A new merchant cannot already have an ID")).body(null);
        }

        MerchantDTO result = merchantService.save(merchantDTO);

        return ResponseEntity.created(new URI("/api/merchants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("merchant", result.getId().toString()))
            .body(result);
    }



    /**
     * PUT  /merchants : Updates an existing merchant.
     *
     * @param merchantDTO the merchantDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated merchantDTO,
     * or with status 400 (Bad Request) if the merchantDTO is not valid,
     * or with status 500 (Internal Server Error) if the merchantDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/merchants")
    @Timed
    public ResponseEntity<MerchantDTO> updateMerchant(@RequestBody MerchantDTO merchantDTO) throws URISyntaxException {
        log.debug("REST request to update Merchant : {}", merchantDTO);
        if (merchantDTO.getId() == null) {
            return createMerchant(merchantDTO);
        }
        MerchantDTO result = merchantService.save(merchantDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("merchant", merchantDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /merchants : get all the merchants.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of merchants in body
     */
    @GetMapping("/merchants")
    @Timed
    public List<MerchantDTO> getAllMerchants() {
        log.debug("REST request to get all Merchants");
        return merchantService.findAll();
    }

    /**
     * GET  /merchants/:id : get the "id" merchant.
     *
     * @param id the id of the merchantDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the merchantDTO, or with status 404 (Not Found)
     */
    @GetMapping("/merchants/{id}")
    @Timed
    public ResponseEntity<MerchantDTO> getMerchant(@PathVariable String id) {
        log.debug("REST request to get Merchant : {}", id);
        MerchantDTO merchantDTO = merchantService.findOne(id);
        return Optional.ofNullable(merchantDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

  /*  @GetMapping("/merchants/{id}/appointments")
    @Timed
    public Appointment getMerchantAppointments(@PathVariable String id){
        log.debug("REST request to get Merchant appointments : {}", id);
        return merchantService.findAppointments(id);
    }*/

    /**
     * DELETE  /merchants/:id : delete the "id" merchant.
     *
     * @param id the id of the merchantDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/merchants/{id}")
    @Timed
    public ResponseEntity<Void> deleteMerchant(@PathVariable String id) {
        log.debug("REST request to delete Merchant : {}", id);
        merchantService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("merchant", id.toString())).build();
    }

}

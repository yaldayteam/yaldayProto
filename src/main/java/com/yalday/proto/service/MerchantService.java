package com.yalday.proto.service;

import com.yalday.proto.domain.Appointment;
import com.yalday.proto.domain.Merchant;
import com.yalday.proto.repository.MerchantRepository;
import com.yalday.proto.service.dto.MerchantDTO;
import com.yalday.proto.service.mapper.MerchantMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;


import org.bson.types.ObjectId;
import com.yalday.proto.service.UserService;


/**
 * Service Implementation for managing Merchant.
 */
@Service
public class MerchantService {

    private final Logger log = LoggerFactory.getLogger(MerchantService.class);

    private final MerchantRepository merchantRepository;

    private final MerchantMapper merchantMapper;

    private final UserService userService;

    public MerchantService(MerchantRepository merchantRepository, MerchantMapper merchantMapper, UserService userService) {
        this.merchantRepository = merchantRepository;
        this.merchantMapper = merchantMapper;
        this.userService = userService;
    }


    /**
     * Save a merchant.
     * @param merchantDTO the entity to save
     * @return the persisted entity
     */
    public MerchantDTO save(MerchantDTO merchantDTO) {
        log.debug("Request to save Merchant : {}", merchantDTO);
        Merchant merchant = merchantMapper.merchantDTOToMerchant(merchantDTO);
        merchant = merchantRepository.save(merchant);
        userService.updateUser(merchant);

        return merchantMapper.merchantToMerchantDTO(merchant);
    }

    /**
     *  Get all the merchants.
     *
     *  @return the list of entities
     */
    public List<MerchantDTO> findAll() {
        log.debug("Request to get all Merchants");

        return merchantRepository.findAll().stream()
            .map(merchantMapper::merchantToMerchantDTO)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get one merchant by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    public MerchantDTO findOne(String id) {
        log.debug("Request to get Merchant : {}", id);
        Merchant merchant = merchantRepository.findOne(id);
        return merchantMapper.merchantToMerchantDTO(merchant);
    }

 /*   public Appointment findAppointments(String id){
        log.debug("Request to get Merchant appointments : {}", id);
        Merchant merchant = merchantRepository.findOne(id);
        return merchant.getResources().get(0).getAppointment();
    }*/

    /**
     *  Delete the  merchant by id.
     *
     *  @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete Merchant : {}", id);
        merchantRepository.delete(id);
        userService.deleteUserMerchant();
    }

    /**
     *  Get one merchant by booking id.
     *
     *  @param id the id of the booking contained in the merchant entity
     *  @return the merchant entity
     */
    public MerchantDTO findOneByBookingId(String id) {

        log.debug("Request to get Merchant : {}", id);
        Merchant merchant = merchantRepository.findOneByBookingsId(new ObjectId(id));

        return merchantMapper.merchantToMerchantDTO(merchant);
    }

    /**
     *  Get one merchant by resource id.
     *
     *  @param id the id of the resource contained in the merchant entity
     *  @return the merchant entity
     */
    public MerchantDTO findOneByResourceId(String id) {

        log.debug("Request to get Merchant : {}", id);
        Merchant merchant = merchantRepository.findOneByResourcesId(new ObjectId(id));

        return merchantMapper.merchantToMerchantDTO(merchant);
    }








}

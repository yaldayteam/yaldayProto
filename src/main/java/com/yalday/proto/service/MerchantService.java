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

import com.yalday.proto.service.UserService;


/**
 * Service Implementation for managing Merchant.
 */
@Service
public class MerchantService {

    private final Logger log = LoggerFactory.getLogger(MerchantService.class);

    @Inject
    private MerchantRepository merchantRepository;

    @Inject
    private MerchantMapper merchantMapper;

    @Inject
    private UserService userService;

    /**
     * Save a merchant.
     *
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

    public Appointment findAppointments(String id){
        log.debug("Request to get Merchant appointments : {}", id);
        Merchant merchant = merchantRepository.findOne(id);
        return merchant.getResources().get(0).getAppointment();
    }

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
}

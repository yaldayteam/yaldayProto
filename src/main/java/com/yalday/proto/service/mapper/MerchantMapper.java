package com.yalday.proto.service.mapper;

import com.yalday.proto.domain.*;
import com.yalday.proto.service.dto.MerchantDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Merchant and its DTO MerchantDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MerchantMapper {

    MerchantDTO merchantToMerchantDTO(Merchant merchant);

    List<MerchantDTO> merchantsToMerchantDTOs(List<Merchant> merchants);

    Merchant merchantDTOToMerchant(MerchantDTO merchantDTO);

    List<Merchant> merchantDTOsToMerchants(List<MerchantDTO> merchantDTOs);
}

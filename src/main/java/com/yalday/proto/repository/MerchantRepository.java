package com.yalday.proto.repository;

import com.yalday.proto.domain.Merchant;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Merchant entity.
 */
@SuppressWarnings("unused")
public interface MerchantRepository extends MongoRepository<Merchant,String> {

}

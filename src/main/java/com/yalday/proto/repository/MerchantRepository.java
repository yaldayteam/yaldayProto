package com.yalday.proto.repository;

import com.yalday.proto.domain.Merchant;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.bson.types.ObjectId;

/**
 * Spring Data MongoDB repository for the Merchant entity.
 */
@SuppressWarnings("unused")
public interface MerchantRepository extends MongoRepository<Merchant,String> {

    public Merchant findOneByBookingsId(ObjectId id);

    public Merchant findOneByResourcesId(ObjectId id);

}


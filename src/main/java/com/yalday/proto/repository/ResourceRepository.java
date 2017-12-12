package com.yalday.proto.repository;

import com.yalday.proto.domain.Resource;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Resource entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ResourceRepository extends MongoRepository<Resource,String> {
    
}

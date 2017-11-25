package com.yalday.proto.repository;

import com.yalday.proto.domain.Booking;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Booking entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BookingRepository extends MongoRepository<Booking,String> {
    
}

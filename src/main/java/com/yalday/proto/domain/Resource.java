package com.yalday.proto.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Created by martin on 12/11/2017.
 */
//TODO Can we think of a better name than resource for what this is?
@Document(collection = "resource")
public class Resource {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field
    private String resourceId;

    @Field
    private Appointment appointment;

    public Resource (){}

    public Resource(Appointment appointment) {
        this.appointment = appointment;
    }

    public Resource resourceId(String resourceId){
        this.resourceId = resourceId;
        return this;
    }

    public Resource appointments(final Appointment appointments){
        this.appointment = appointments;
        return this;
    }

    /**
     * A resource can have multiple appointment
     * @return a boolean value representing whether a resource is fully allocated
     */
    public boolean isFullyBooked(){
        return true;
    }
}

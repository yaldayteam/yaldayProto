package com.yalday.proto.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

/**
 * Created by martin on 12/11/2017.
 */
@Document(collection = "appointment")
public class Appointment {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    /**
     * Change this name to something meaningful
     * Right now it stores information about the appointment
     */
    @Field
    private String text;

    /**
     * Use Joda or not?
     */
    @Field
    private Date startTime;

    @Field
    private Date endTime;

    public Appointment(final String text,
                       final Date startTime,
                       final Date endTime) {
        this.text = text;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     *
     * @param appointment a potentially conflicting appointment
     * @return a boolean representing whether the passed in appointment
     * conflicts with this appointment
     */
    public boolean overlaps(final Appointment appointment){
        return this.startTime.before(appointment.endTime) ? true : false;
    }
}

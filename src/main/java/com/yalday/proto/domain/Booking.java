package com.yalday.proto.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Booking.
 */
@Document(collection = "booking")
public class Booking implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String id;

    @Field("text")
    private String text;

    @Field("start_date")
    private ZonedDateTime startDate;

    @Field("end_date")
    private ZonedDateTime endDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public Booking text(String text) {
        this.text = text;
        return this;
    }

    public void setText(String text) {
        this.text = text;
    }


    public ZonedDateTime getStartDate(){

        return startDate;

    }

    public Booking startDate(ZonedDateTime startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }


   public ZonedDateTime getEndDate(){
       return endDate;
   }

    public Booking endDate(ZonedDateTime endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(ZonedDateTime endDate) {
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Booking booking = (Booking) o;
        if (booking.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), booking.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Booking{" +
            "id=" + getId() +
            ", text='" + getText() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            "}";
    }
}

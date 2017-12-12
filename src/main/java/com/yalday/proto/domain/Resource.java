package com.yalday.proto.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.io.Serializable;
import java.util.Objects;
import java.util.List;
import com.yalday.proto.domain.Booking;

/**
 * A Resource.
 */
@Document(collection = "resource")
public class Resource implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String id;

    @Field("text")
    private String text;

    @Field("color")
    private String color;

    @Field("capacity")
    private int capacity;

    @Field("multiplebooking")
    private Boolean multiplebooking;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public Resource text(String text) {
        this.text = text;
        return this;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getColor() {
        return color;
    }

    public Resource color(String color) {
        this.color = color;
        return this;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getCapacity() {
        return capacity;
    }

    public Resource capacity(int capacity) {
        this.capacity = capacity;
        return this;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Boolean isMultiplebooking() {
        return multiplebooking;
    }

    public Resource multiplebooking(Boolean multiplebooking) {
        this.multiplebooking = multiplebooking;
        return this;
    }

    public void setMultiplebooking(Boolean multiplebooking) {
        this.multiplebooking = multiplebooking;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Resource resource = (Resource) o;
        if (resource.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), resource.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Resource{" +
            "id=" + getId() +
            ", text='" + getText() + "'" +
            ", color='" + getColor() + "'" +
            ", capacity='" + getCapacity() + "'" +
            ", multiplebooking='" + isMultiplebooking() + "'" +
            "}";
    }
}

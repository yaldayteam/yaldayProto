package com.yalday.proto.service.dto;

import com.yalday.proto.domain.Resource;
import com.yalday.proto.domain.Booking;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.ArrayList;

/**
 * A DTO for the Merchant entity.
 */
public class MerchantDTO implements Serializable {

    private String id;

    private String name;

    private String description;

    private String address;

    private String city;

    private String postcode;

    private String country;

    private String category;

    private String backgroundColor;

    private String email;

    private String phonenumber;

    private String userid;

    private List<Resource> resources;

    private List<Booking> bookings;

    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }


    public void addResource(Resource resource){

        if(resources == null)
        {
            resources = new ArrayList<Resource>();
            resources.add(resource);
        }
        else
        {
            resources.add(resource);
        }
    }

    public void updateResource(Resource resource) {

        if(resources == null)
        {
            resources = new ArrayList<Resource>();
            resources.add(resource);
        }
        else
        {
            for (int i = 0; i < resources.size(); i++)
            {
                Resource elementResource = resources.get(i);
                if (elementResource.equals(resource))
                {
                    resources.set(i, resource);
                    i = resources.size();
                }
            }
        }
    }


    public void deleteResource(String id) {

        if(resources != null)
        {
            for (int i = 0; i < resources.size(); i++)
            {
                Resource elementResource = resources.get(i);

                if (elementResource.getId().equals(id))
                {
                    resources.remove(i);
                    i = resources.size() + 1;
                }
            }
        }
    }



    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public void addBooking(Booking booking){

        if(bookings == null)
        {
            bookings = new ArrayList<Booking>();
            bookings.add(booking);
        }
        else
        {
            bookings.add(booking);
        }
    }

    public void updateBooking(Booking booking) {

        if(bookings == null)
        {
            bookings = new ArrayList<Booking>();
            bookings.add(booking);
        }
        else
        {
            for (int i = 0; i < bookings.size(); i++)
            {
                Booking elementBooking = bookings.get(i);
                if (elementBooking.equals(booking))
                {
                    bookings.set(i, booking);
                    i = bookings.size();
                }
            }
        }
    }

    public void deleteBooking(String id) {

        if(bookings != null)
        {
            for (int i = 0; i < bookings.size(); i++)
            {
                Booking elementBooking = bookings.get(i);

                if (elementBooking.getId().equals(id))
                {
                    bookings.remove(i);
                    i = bookings.size() + 1;
                }
            }
        }
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }


    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Override
    public String toString() {
        return "MerchantDTO{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", address='" + address + '\'' +
            ", city='" + city + '\'' +
            ", postcode='" + postcode + '\'' +
            ", country='" + country + '\'' +
            ", category='" + category + '\'' +
            ", backgroundColor='" + backgroundColor + '\'' +
            ", email='" + email + '\'' +
            ", phonenumber='" + phonenumber + '\'' +
            ", userid='" + userid + '\'' +
            ", resources='" + resources + '\'' +
            ", bookings=" + bookings +
            '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MerchantDTO merchantDTO = (MerchantDTO) o;

        if ( ! Objects.equals(id, merchantDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}

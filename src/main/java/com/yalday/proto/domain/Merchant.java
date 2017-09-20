package com.yalday.proto.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

import com.yalday.proto.domain.enumeration.Category;

/**
 * A Merchant.
 */

@Document(collection = "merchant")
public class Merchant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Size(min = 2, max = 50)
    @Field("name")
    private String name;

    @NotNull
    @Size(min = 5, max = 256)
    @Field("description")
    private String description;

    @Field("address")
    private String address;

    @Field("city")
    private String city;

    @Field("postcode")
    private String postcode;

    @Field("country")
    private String country;

    @Field("category")
    private Category category;

    @Field("bg_color")
    private String bgColor;

    @Field("text_color")
    private String textColor;

    @Field("email")
    private String email;

    @Pattern(regexp = "[0-9]*$")
    @Field("phone_number")
    private String phoneNumber;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Merchant name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Merchant description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public Merchant address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public Merchant city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostcode() {
        return postcode;
    }

    public Merchant postcode(String postcode) {
        this.postcode = postcode;
        return this;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCountry() {
        return country;
    }

    public Merchant country(String country) {
        this.country = country;
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Category getCategory() {
        return category;
    }

    public Merchant category(Category category) {
        this.category = category;
        return this;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getBgColor() {
        return bgColor;
    }

    public Merchant bgColor(String bgColor) {
        this.bgColor = bgColor;
        return this;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }

    public String getTextColor() {
        return textColor;
    }

    public Merchant textColor(String textColor) {
        this.textColor = textColor;
        return this;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public String getEmail() {
        return email;
    }

    public Merchant email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Merchant phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Merchant merchant = (Merchant) o;
        if(merchant.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, merchant.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Merchant{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", description='" + description + "'" +
            ", address='" + address + "'" +
            ", city='" + city + "'" +
            ", postcode='" + postcode + "'" +
            ", country='" + country + "'" +
            ", category='" + category + "'" +
            ", bgColor='" + bgColor + "'" +
            ", textColor='" + textColor + "'" +
            ", email='" + email + "'" +
            ", phoneNumber='" + phoneNumber + "'" +
            '}';
    }
}

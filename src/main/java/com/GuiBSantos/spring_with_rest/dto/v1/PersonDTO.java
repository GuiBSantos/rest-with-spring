package com.GuiBSantos.spring_with_rest.dto.v1;

//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import com.fasterxml.jackson.annotation.JsonPropertyOrder;

//import com.GuiBSantos.spring_with_rest.serializer.GenderSerializer;
//import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
//import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;


//@JsonPropertyOrder({"id", "first_name", "last_name", "address", "gender"})
//@JsonFilter("PersonFilter")
@JacksonXmlRootElement(localName = "Person")
@Relation(collectionRelation = "people")
public class PersonDTO extends RepresentationModel<PersonDTO> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

//    @JsonProperty("first_name")
    private String firstName;

//    @JsonProperty("last_name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String lastName;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String phoneNumber;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date birthDay;

    private String address;

//    @JsonIgnore
//    @JsonSerialize(using = GenderSerializer.class)
    private String gender;

    private Boolean enabled;

//    private String sensitiveData;

    public PersonDTO() {
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String adress) {
        this.address = adress;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PersonDTO personDTO)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(getId(), personDTO.getId()) && Objects.equals(getFirstName(), personDTO.getFirstName()) && Objects.equals(getLastName(), personDTO.getLastName()) && Objects.equals(getPhoneNumber(), personDTO.getPhoneNumber()) && Objects.equals(getBirthDay(), personDTO.getBirthDay()) && Objects.equals(getAddress(), personDTO.getAddress()) && Objects.equals(getGender(), personDTO.getGender()) && Objects.equals(getEnabled(), personDTO.getEnabled());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getId(), getFirstName(), getLastName(), getPhoneNumber(), getBirthDay(), getAddress(), getGender(), getEnabled());
    }
}

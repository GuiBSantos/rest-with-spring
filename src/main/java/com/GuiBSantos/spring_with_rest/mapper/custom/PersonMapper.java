package com.GuiBSantos.spring_with_rest.mapper.custom;

import com.GuiBSantos.spring_with_rest.dto.v2.PersonDTOV2;
import com.GuiBSantos.spring_with_rest.model.Person;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PersonMapper {

    public PersonDTOV2 convertEntityToDTO(Person person) {
        PersonDTOV2 dto = new PersonDTOV2();

        dto.setId(person.getId());
        dto.setFirstName(person.getFirstName());
        dto.setLastName(person.getLastName());
        dto.setBirthDay(new Date());
        dto.setAddress(person.getAddress());
        dto.setGender(person.getGender());

        return dto;
    }

    public Person convertDTOToEntity(PersonDTOV2 person) {

        Person entity = new Person();

        entity.setId(person.getId());
        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
//        entity.setBirthDay(new Date());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        return entity;
    }
}

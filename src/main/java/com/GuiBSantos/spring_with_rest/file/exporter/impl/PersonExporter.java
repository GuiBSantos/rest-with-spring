package com.GuiBSantos.spring_with_rest.file.exporter.impl;

import com.GuiBSantos.spring_with_rest.dto.v1.PersonDTO;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface PersonExporter {

    Resource exportPeople(List<PersonDTO> people) throws Exception;
    Resource exportPerson(PersonDTO person) throws Exception;
}

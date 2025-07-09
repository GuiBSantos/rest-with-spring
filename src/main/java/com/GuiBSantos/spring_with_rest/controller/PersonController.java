package com.GuiBSantos.spring_with_rest.controller;

import com.GuiBSantos.spring_with_rest.docs.PersonControllerDocs;
import com.GuiBSantos.spring_with_rest.dto.v1.PersonDTO;
import com.GuiBSantos.spring_with_rest.file.exporter.MediaTypes;
import com.GuiBSantos.spring_with_rest.service.PersonService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/person/v1")
@Tag(name = "People", description = "Endpoints for Managing People")
public class PersonController implements PersonControllerDocs {

    @Autowired
    private PersonService service;
    // private PersonServices service = new PersonServices();

//    @RequestMapping(method = RequestMethod.GET,
//            produces = MediaType.APPLICATION_JSON_VALUE
//    )

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE})
    @Override
    public ResponseEntity<PagedModel<EntityModel<PersonDTO>>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "12") Integer size,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction

    ) {
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
        Pageable pageble = PageRequest.of(page, size, Sort.by(sortDirection, "firstName"));
            return ResponseEntity.ok(service.findAll(pageble));
        }

    @GetMapping(value = "/exportPage",
            produces = {MediaTypes.APPLICATION_XLSX_VALUE, MediaTypes.APPLICATION_CSV_VALUE, MediaTypes.APPLICATION_PDF_VALUE})
    @Override
    public ResponseEntity<Resource> exportPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "12") Integer size,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction,
            HttpServletRequest request
    ) {
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
        Pageable pageble = PageRequest.of(page, size, Sort.by(sortDirection, "firstName"));

        String acceptHeader = request.getHeader(HttpHeaders.ACCEPT);

        Resource file = service.exportPage(pageble, acceptHeader);

        var contentType = acceptHeader != null ? acceptHeader : "application/octet-stream";
        var fileExtension = MediaTypes.APPLICATION_XLSX_VALUE.equalsIgnoreCase(acceptHeader) ? ".xlsx" : ".csv";
        var filename = "people_exported" + fileExtension;

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename\"" + fileExtension + "\"").body(file);
    }

    @GetMapping(value = "/findPeopleByName/{firstName}",produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE})
    @Override
    public ResponseEntity<PagedModel<EntityModel<PersonDTO>>> findByName(
            @PathVariable("firstName") String firstName,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "12") Integer size,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction

    ) {
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
        Pageable pageble = PageRequest.of(page, size, Sort.by(sortDirection, "firstName"));
        return ResponseEntity.ok(service.findByName(firstName, pageble));
    }

//    @CrossOrigin(origins = {"http://localhost:8080", "http://localhost:8081"})
    @GetMapping(value = "/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE}
    )
    @Override
    public PersonDTO findById(@PathVariable("id") Long id) {
        var person = service.findById(id);
        person.setBirthDay(new Date());
        person.setPhoneNumber("+55 (34) 98765-4321");
//        person.setSensitiveData("Foo Bar");

        return person;
    }

//    @CrossOrigin(origins = "http://localhost:8080")
    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE}
    )
    @Override
    public PersonDTO create(@RequestBody PersonDTO PersonDTO) {
        return service.create(PersonDTO);
    }

    @PostMapping(value = "/massCreation",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE}
    )
    @Override
    public List<PersonDTO> massCreation(@RequestParam("file") MultipartFile file) {
        return service.massCreation(file);
    }

    @PutMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE}
    )
    @Override
    public PersonDTO update(@RequestBody PersonDTO PersonDTO) {
        return service.update(PersonDTO);
    }


    @PatchMapping(value = "/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE}
    )
    @Override
    public PersonDTO disablePerson(@PathVariable("id") Long id) {
        return service.disablePerson(id);
    }

    @DeleteMapping(value = "/{id}"
    )
    @Override
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    // V2

//    @PostMapping(value = "/v2",
//            consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE},
//            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_YAML_VALUE}
//    )
//    public PersonDTOV2 create(@RequestBody PersonDTOV2 PersonDTOV2) {
//        return service.createV2(PersonDTOV2);
//    }
}

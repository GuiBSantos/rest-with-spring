package com.GuiBSantos.spring_with_rest.service;

import com.GuiBSantos.spring_with_rest.controller.PersonController;
import com.GuiBSantos.spring_with_rest.dto.v1.PersonDTO;
import com.GuiBSantos.spring_with_rest.dto.v2.PersonDTOV2;
import com.GuiBSantos.spring_with_rest.exception.BadRequestException;
import com.GuiBSantos.spring_with_rest.exception.FileStorageException;
import com.GuiBSantos.spring_with_rest.exception.RequiredObjectIsNullException;
import com.GuiBSantos.spring_with_rest.exception.ResourceNotFoundException;
import com.GuiBSantos.spring_with_rest.file.exporter.MediaTypes;
import com.GuiBSantos.spring_with_rest.file.exporter.contract.FileExporter;
import com.GuiBSantos.spring_with_rest.file.exporter.factory.FileExporterFactory;
import com.GuiBSantos.spring_with_rest.file.exporter.impl.PersonExporter;
import com.GuiBSantos.spring_with_rest.file.importer.contract.FileImporter;
import com.GuiBSantos.spring_with_rest.file.importer.factory.FileImporterFactory;
import com.GuiBSantos.spring_with_rest.mapper.custom.PersonMapper;
import com.GuiBSantos.spring_with_rest.model.Person;
import com.GuiBSantos.spring_with_rest.repository.PersonRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import static com.GuiBSantos.spring_with_rest.mapper.ObjectMapper.parseObject;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class PersonService {

    @Autowired
    private PersonRepository repository;

    @Autowired
    private PersonMapper personMapper;

    @Autowired
    FileImporterFactory fileImporter;

    @Autowired
    FileExporterFactory fileExporterFactory;

    @Autowired
    private PagedResourcesAssembler<PersonDTO> assembler;

    private Logger logger = Logger.getLogger(PersonService.class.getName());

    public PagedModel<EntityModel<PersonDTO>> findAll(Pageable pageable) {

        logger.info("Finding all People!");

        var people = repository.findAll(pageable);

        return buildPagedModel(pageable, people);
    }

    public PagedModel<EntityModel<PersonDTO>> findByName(String firstName, Pageable pageable) {

        logger.info("Finding people by name!");

        var people = repository.findPeopleByName(firstName, pageable);

        return buildPagedModel(pageable, people);
    }

    private PagedModel<EntityModel<PersonDTO>> buildPagedModel(Pageable pageable, Page<Person> people) {
        var peopleWithLinks = people.map(person -> {
            var dto = parseObject(person, PersonDTO.class);
            addHateoasLinks(dto);
            return dto;
        });

        Link findAllLink = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(PersonController.class)
                        .findAll(pageable.getPageNumber(), pageable.getPageSize(), String.valueOf(pageable.getSort()))).withSelfRel();

        return assembler.toModel(peopleWithLinks, findAllLink);
    }

    public Resource exportPage(Pageable pageable, String acceptHeader) {

        logger.info("Exporting a People page!");

        var people = repository.findAll(pageable)
                .map(person -> parseObject(person, PersonDTO.class))
                .getContent();

        try {
            FileExporter exporter = this.fileExporterFactory.getExporter(acceptHeader);
            return exporter.exportFile(people);
        } catch (Exception e) {
            throw new RuntimeException("Error during file export",e);
        }

    }

    public PersonDTO findById(Long id) {
        logger.info("Finding one Person!");

       var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

       var dto = parseObject(entity, PersonDTO.class);

        addHateoasLinks(dto);

        return dto;
    }

    public PersonDTO create(PersonDTO personDTO) {

        if (personDTO == null) throw new RequiredObjectIsNullException();

        logger.info("Creating one Person!");

        var entity = parseObject(personDTO, Person.class);

       var dto = parseObject(repository.save(entity), PersonDTO.class);

       addHateoasLinks(dto);

       return dto;
    }

    public List<PersonDTO> massCreation(MultipartFile file) {

        logger.info("Importing people from file!");

        if(file.isEmpty()) throw new BadRequestException("Please, set a valid file");

        try (InputStream inputStream = file.getInputStream()) {
            String fileName = Optional.ofNullable(file.getOriginalFilename()).orElseThrow(() -> new BadRequestException("File cannot be null"));
            FileImporter fileImporter = this.fileImporter.getImporter(fileName);

            List<Person> entities = fileImporter.importFile(inputStream).stream().map(personDTO -> repository.save(parseObject(personDTO, Person.class))).toList();
            return entities.stream().map(entity -> {
                var dto = parseObject(entity, PersonDTO.class);
                addHateoasLinks(dto);
                return dto;
            }).toList();
        } catch (Exception e) {
            throw new FileStorageException("Error processing the file");
        }

    }

    public PersonDTO update(PersonDTO personDTO) {

        if (personDTO == null) throw new RequiredObjectIsNullException();

        logger.info("Updating one Person!");

        Person entity = repository.findById(personDTO.getId()).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

        entity.setFirstName(personDTO.getFirstName());
        entity.setLastName(personDTO.getLastName());
        entity.setAddress(personDTO.getAddress());
        entity.setGender(personDTO.getGender());

        var dto = parseObject(repository.save(entity), PersonDTO.class);

        addHateoasLinks(dto);

        return dto;
    }

    @Transactional
    public PersonDTO disablePerson(Long id) {

        logger.info("Disabling one Person!");

        repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

        repository.disablePerson(id);

        var entity = repository.findById(id).get();

        var dto = parseObject((entity), PersonDTO.class);
        addHateoasLinks(dto);
        return dto;

    }

    public void delete(Long id) {

        logger.info("Deleting one Person!");

        Person entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

        repository.delete(entity);

    }

    // V2
    public PersonDTOV2 createV2(PersonDTOV2 personDTOV2) {

        logger.info("Creating one Person V2!");

        var entity = personMapper.convertDTOToEntity(personDTOV2);

        return personMapper.convertEntityToDTO(repository.save(entity));
    }

    private void addHateoasLinks(PersonDTO dto) {

        // FindById
        dto.add(linkTo(methodOn(PersonController.class).findById(dto.getId())).withSelfRel().withType("GET"));
        // Delete
        dto.add(linkTo(methodOn(PersonController.class).delete(dto.getId())).withRel("delete").withType("DELETE"));
        // FindAll
        dto.add(linkTo(methodOn(PersonController.class).findAll(1, 12, "asc")).withRel("findAll").withType("GET"));
        // FindByName
        dto.add(linkTo(methodOn(PersonController.class).findByName("", 1, 12, "asc")).withRel("findByName").withType("GET"));
        // Create
        dto.add(linkTo(methodOn(PersonController.class).create(dto)).withRel("create").withType("POST"));

        dto.add(linkTo(methodOn(PersonController.class)).slash("massCreation").withRel("massCreation").withType("POST"));
        // Update
        dto.add(linkTo(methodOn(PersonController.class).update(dto)).withRel("update").withType("PUT"));
        // Disable
        dto.add(linkTo(methodOn(PersonController.class).disablePerson(dto.getId())).withRel("disable").withType("PATCH"));

        dto.add(linkTo(methodOn(PersonController.class).exportPage(1, 12, "asc", null)).withRel("exportPage").withType("GET").withTitle("Export People"));


    }
}

package com.GuiBSantos.spring_with_rest.docs;

import com.GuiBSantos.spring_with_rest.dto.v1.PersonDTO;
import com.GuiBSantos.spring_with_rest.file.exporter.MediaTypes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PersonControllerDocs {
    @Operation(summary = "Find All People", description = "Find All People", tags = "People", responses = {
            @ApiResponse(
                    description = "Success",
                    responseCode = "200",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = PersonDTO.class))
                            )
                    }),
            @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
    })
    ResponseEntity<PagedModel<EntityModel<PersonDTO>>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "12") Integer size,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction

    );

    @Operation(summary = "Export People", description = "Export a Page of People in XLSX and CSV format", tags = "People", responses = {
            @ApiResponse(
                    description = "Success",
                    responseCode = "200",
                    content = {
                            @Content(mediaType = MediaTypes.APPLICATION_XLSX_VALUE),
                            @Content(mediaType = MediaTypes.APPLICATION_CSV_VALUE)
                    }),
            @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
    })
    ResponseEntity<Resource> exportPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "12") Integer size,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction,
            HttpServletRequest request

    );

    @Operation(summary = "Massive People Creation", description = "Massive People Creation with upload of XLSX or CSV", tags = "People", responses = {
            @ApiResponse(
                    description = "Success",
                    responseCode = "200",
                    content = {
                            @Content(
                                    schema = @Schema(implementation = PersonDTO.class)
                            )
                    }),
            @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
    })
    List<PersonDTO> massCreation(MultipartFile file);

    @Operation(summary = "Find By Name", description = "Find people that contains a specific name", tags = "People", responses = {
            @ApiResponse(
                    description = "Success",
                    responseCode = "200",
                    content = {
                            @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = PersonDTO.class))
                            )
                    }),
            @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
    })
    ResponseEntity<PagedModel<EntityModel<PersonDTO>>> findByName(
            @PathVariable("firstName") String firstName,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "12") Integer size,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction

    );

    @Operation(summary = "Finds a person", description = "Find a specific person by your ID", tags = "People", responses = {
            @ApiResponse(
                    description = "Success",
                    responseCode = "200",
                    content =
                    @Content(schema = @Schema(implementation = PersonDTO.class)
                    )
            ),
            @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
    })
    PersonDTO findById(@PathVariable("id") Long id);


    @Operation(summary = "Create a person", description = "Method that allow to create a person", tags = "People", responses = {
            @ApiResponse(
                    description = "Success",
                    responseCode = "200",
                    content =
                    @Content(schema = @Schema(implementation = PersonDTO.class)
                    )
            ),
            @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
    })
    PersonDTO create(@RequestBody PersonDTO PersonDTO);


    @Operation(summary = "Update a person", description = "Method that allow to update infos from a person", tags = "People", responses = {
            @ApiResponse(
                    description = "Success",
                    responseCode = "200",
                    content =
                    @Content(schema = @Schema(implementation = PersonDTO.class)
                    )
            ),
            @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
    })
    PersonDTO update(@RequestBody PersonDTO PersonDTO);

    @Operation(summary = "Disable a person", description = "Disable a specific person by your ID", tags = "People", responses = {
            @ApiResponse(
                    description = "Success",
                    responseCode = "200",
                    content =
                    @Content(schema = @Schema(implementation = PersonDTO.class)
                    )
            ),
            @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
    })
    PersonDTO disablePerson(@PathVariable("id") Long id);

    @Operation(summary = "Delete a person", description = "Method that allow to delete a person", tags = "People", responses = {
            @ApiResponse(
                    description = "Success",
                    responseCode = "200",
                    content =
                    @Content(schema = @Schema(implementation = PersonDTO.class)
                    )
            ),
            @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
    })
    ResponseEntity<?> delete(@PathVariable("id") Long id);
}

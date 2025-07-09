package com.GuiBSantos.spring_with_rest.file.importer.contract;

import com.GuiBSantos.spring_with_rest.dto.v1.PersonDTO;

import java.io.InputStream;
import java.util.List;

public interface FileImporter {

    List<PersonDTO> importFile(InputStream inputStream) throws Exception;
}

package com.GuiBSantos.spring_with_rest.file.exporter.contract;

import com.GuiBSantos.spring_with_rest.dto.v1.PersonDTO;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface FileExporter {

    Resource exportFile(List<PersonDTO> people) throws Exception;
}

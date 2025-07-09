package com.GuiBSantos.spring_with_rest.file.exporter.factory;

import com.GuiBSantos.spring_with_rest.exception.BadRequestException;
import com.GuiBSantos.spring_with_rest.file.exporter.MediaTypes;
import com.GuiBSantos.spring_with_rest.file.exporter.contract.FileExporter;
import com.GuiBSantos.spring_with_rest.file.exporter.impl.CsvExporter;
import com.GuiBSantos.spring_with_rest.file.exporter.impl.XlsxExporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class FileExporterFactory {

    private Logger logger = LoggerFactory.getLogger(FileExporterFactory.class);

    @Autowired
    private ApplicationContext context;

    public FileExporter getExporter(String acceptHeader) throws Exception {
        if (acceptHeader.equalsIgnoreCase(MediaTypes.APPLICATION_XLSX_VALUE)) {
            return context.getBean(XlsxExporter.class);
        } else if (acceptHeader.equalsIgnoreCase(MediaTypes.APPLICATION_CSV_VALUE)) {
            return context.getBean(CsvExporter.class);
        } else {
            throw new BadRequestException("Invalid file format!");
        }
    }
}

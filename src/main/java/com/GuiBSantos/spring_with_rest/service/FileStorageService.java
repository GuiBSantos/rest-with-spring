package com.GuiBSantos.spring_with_rest.service;

import com.GuiBSantos.spring_with_rest.config.FileStorageConfig;
import com.GuiBSantos.spring_with_rest.controller.FileController;
import com.GuiBSantos.spring_with_rest.exception.FileNotFoundException;
import com.GuiBSantos.spring_with_rest.exception.FileStorageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    public FileStorageService(FileStorageConfig fileStorageConfig) {
        Path path = Paths.get(fileStorageConfig.getUploadDir()).toAbsolutePath().normalize();

        this.fileStorageLocation = path;
        try {
            logger.info("Creating Directories");
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception e) {
            logger.error("Could not create the directory where files will be stored");
            throw new FileStorageException("Could not create the directory where files will be stored", e);
        }
    }

    public String storeFile(MultipartFile file) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            if(fileName.contains("..")) {
                logger.error("Sorry, filename contains a invalid path sequence " + fileName);
                throw new FileStorageException("Sorry, filename contains a invalid path sequence " + fileName);
            }

            logger.info("Saving file in Disk");

            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (Exception e) {
            logger.error("Could not store file " + fileName + ". Please try again!");
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", e);
        }
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new FileNotFoundException("File noite found " + fileName);
            }
        } catch (Exception e) {
            throw new FileNotFoundException("File noite found " + fileName, e);
        }
    }
}

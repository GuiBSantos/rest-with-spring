package com.GuiBSantos.spring_with_rest.controller;

import com.GuiBSantos.spring_with_rest.docs.EmailControllerDocs;
import com.GuiBSantos.spring_with_rest.dto.v1.request.EmailRequestDTO;
import com.GuiBSantos.spring_with_rest.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/email/v1")
public class EmailController implements EmailControllerDocs {

    @Autowired
    private EmailService emailService;

    @PostMapping
    @Override
    public ResponseEntity<String> sendEmail(@RequestBody EmailRequestDTO emailRequest) {
        emailService.sendSimpleEmail(emailRequest);
        return new ResponseEntity<>("E-mail send with success", HttpStatus.OK);
    }

    @PostMapping(value = "/withAttachment", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Override
    public ResponseEntity<String> sendEmailWithAttachment(@RequestParam("emailRequest") String emailRequest, @RequestParam("attachment") MultipartFile attachment) {
       emailService.sendEmailWithAttachment(emailRequest, attachment);
       return new ResponseEntity<>("E-mail with attachment sent successfully", HttpStatus.OK);
    }
}

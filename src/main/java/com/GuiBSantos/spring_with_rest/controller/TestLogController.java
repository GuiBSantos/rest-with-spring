package com.GuiBSantos.spring_with_rest.controller;

import com.GuiBSantos.spring_with_rest.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/test/v1")
public class TestLogController {

    private Logger logger = LoggerFactory.getLogger(TestLogController.class.getName());

    @GetMapping
    public String testLog() {
        logger.info("This is an INFO log");
        logger.warn("This is an WARN log");
        logger.debug("This is an DEBUG log");
        logger.trace("This is an TRACE log");

        return "Logs generated sucessfully!";
    }
}

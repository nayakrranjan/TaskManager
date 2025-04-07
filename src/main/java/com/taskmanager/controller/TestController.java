package com.taskmanager.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.taskmanager.TaskManagerApplication.logger;

@RestController
public class TestController {

    @GetMapping("/test")
    public String test() {
        logger.warn("This is a test");
        return "Hello World";
    }
}

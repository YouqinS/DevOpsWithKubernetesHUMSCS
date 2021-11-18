package com.youqin.logoutput;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @Autowired
    private StatusService service;

    @GetMapping("/")
    public String hello() {
        return "Greetings from Spring Boot!";
    }

    @GetMapping("/status")
    public String getStatus() {
        return service.getStatus();
    }
}
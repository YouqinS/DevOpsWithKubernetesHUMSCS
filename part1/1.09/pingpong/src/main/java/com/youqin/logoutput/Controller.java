package com.youqin.logoutput;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    private int counter = 0;

    @Autowired
    private ServicePong service;

    @GetMapping("/")
    public String hello() {
        return "Greetings from Spring Boot!";
    }

    @GetMapping("/pingpong")
    public String getPong() {
        String toReturn = service.getPong() + counter;
        counter += 1;
        return toReturn;
    }
}
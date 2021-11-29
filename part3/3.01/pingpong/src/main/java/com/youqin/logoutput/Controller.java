package com.youqin.logoutput;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @Autowired
    private ServicePong service;


    @GetMapping("/")
    public String hello() {
        return "Greetings from ping pong";
    }

    @GetMapping("/pingpong")
    public String getPong() {
        return service.getPong();
    }



}
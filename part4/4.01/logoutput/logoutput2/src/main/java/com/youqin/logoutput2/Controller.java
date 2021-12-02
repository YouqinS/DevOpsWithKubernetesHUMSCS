package com.youqin.logoutput2;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @Autowired
    private OutputService service;

    @GetMapping("/")
    public String hello() {
        return "Greetings from logoutput";
    }

    @GetMapping("/status")
    public String getStatus() {
            return service.getStatus();
    }

    @GetMapping("/health")
    public String getPingPong() {
        return service.getPingPong();
    }
}
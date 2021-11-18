package com.youqin.logoutput2;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @Autowired
    private HashService service;

    @GetMapping("/")
    public String getStatus() {
            return service.getStatus();
    }
}
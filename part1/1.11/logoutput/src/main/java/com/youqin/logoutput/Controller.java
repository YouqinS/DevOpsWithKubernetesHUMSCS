package com.youqin.logoutput;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@RestController
public class Controller {

    @Autowired
    private StatusService service;

    @GetMapping("/")
    public String hello() {
        return "Hash-Pingpong";
    }

    @GetMapping("/status")
    public String getStatus() {
        return service.getStatus();
    }


}
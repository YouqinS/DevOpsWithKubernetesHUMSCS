package com.youqin.pingpong;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
public class Controller implements ErrorController {
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

    @GetMapping("/health")
    public boolean checkDbReadiness() {
        try {
            var isValid = service.isDbReady();
            System.out.println("health:"+isValid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @RequestMapping("/error")
    @ResponseBody
    public String getErrorPath() {
        return "<center><h1>Something went wrong in ping pong </h1></center>";
    }

}
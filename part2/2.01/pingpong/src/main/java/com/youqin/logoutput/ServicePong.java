package com.youqin.logoutput;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@org.springframework.stereotype.Service
public class ServicePong {
    private int counter = 0;

    public String getPong() {
        String pong = "pong" + counter;
        counter += 1;
        return pong;
    }
}

package com.youqin.logoutput;

import org.springframework.beans.factory.annotation.Autowired;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@org.springframework.stereotype.Service
public class ServicePong {
    @Autowired
    private CounterRepository repository;

    public String getPong() {

        var counters = repository.findAll();
        Counter counter;
        if (counters.isEmpty()) {
            counter = new Counter();
            counter.setCount(1);
        } else {
            counter = counters.get(0);
            counter.increment();
        }

        repository.save(counter);
        String pong = "pong" + counter.getCount();
        return pong;
    }
}

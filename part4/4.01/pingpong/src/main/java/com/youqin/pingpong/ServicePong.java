package com.youqin.pingpong;

import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.SQLException;

@org.springframework.stereotype.Service
public class ServicePong {
    @Autowired
    private CounterRepository repository;

    @Autowired
    DataSource dataSource;

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

    public boolean isDbReady() throws SQLException {
        return dataSource.getConnection().isValid(1000);
    }
}

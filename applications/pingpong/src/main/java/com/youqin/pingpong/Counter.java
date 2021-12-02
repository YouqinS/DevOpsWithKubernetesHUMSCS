package com.youqin.pingpong;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Counter {
    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private Integer count;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
    public Integer increment() {
        count = count +1;
        return count;
    }
}

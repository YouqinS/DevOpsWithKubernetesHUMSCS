package com.youqin.logoutput1;

import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
public class StatusService {
    private final UUID uuid = UUID.randomUUID();
    final String randomString =  uuid.toString();
    final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");


    public String getStatus() {
        return String.format("%s: %s", formatter.format(new Date()), randomString);
    }
}

package com.youqin.logoutput;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
public class StatusService {

    final UUID uuid = UUID.randomUUID();

    public String getStatus() {
        final String randomString =  uuid.toString();
        final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        String uuidTimestamp = String.format("%s: %s", formatter.format(new Date()), randomString);
        String pingpong = getPingPong();
        pingpong = pingpong.replace("pong", "");
        pingpong = "Ping / Pongs: " + pingpong;
        return uuidTimestamp + "\n" + pingpong;
    }

    private static String getPingPong() {
        RestTemplate restTemplate = new RestTemplate();
        String pingpongUrl = "http://localhost:8888/pingpong";
        ResponseEntity<String> response
                = restTemplate.getForEntity(pingpongUrl, String.class);
        return response.getBody();
    }
}

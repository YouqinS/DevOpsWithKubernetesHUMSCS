package com.youqin.logoutput2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

@Service
public class OutputService {


    @Value("${pingpong.host:localhost}")
    private String pingponghost;

    @Value("${pingpong.port:9999}")
    private String pingpongport;


    public String getStatus() {
        String uuidTimestamp = readStringFromFile();
        String pingpong = getPingPong();
        pingpong = pingpong.replace("pong", "");
        pingpong = "Ping / Pongs: " + pingpong;
        String message =  System.getenv("MESSAGE");
        return message + "\n" +  uuidTimestamp + "\n" + pingpong;
    }

    public String readStringFromFile() {
        String toR = "--------";
        try {
            File myObj = new File("./files/output.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                toR = myReader.nextLine();
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return toR;
    }

    public String getPingPong() {
        RestTemplate restTemplate = new RestTemplate();

        String pingpongUrl = "http://" + pingponghost + ":" + pingpongport + "/pingpong";

        ResponseEntity<String> response = restTemplate.getForEntity(pingpongUrl, String.class);
        return response.getBody();
    }
}

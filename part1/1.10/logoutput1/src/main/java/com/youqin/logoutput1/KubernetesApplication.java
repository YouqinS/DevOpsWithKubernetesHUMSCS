package com.youqin.logoutput1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

@SpringBootApplication
public class KubernetesApplication {


    public static void main(String[] args) throws IOException {
        SpringApplication.run(KubernetesApplication.class, args);

        Timer timer = new Timer();

        UUID uuid = UUID.randomUUID();
        final String randomString = uuid.toString();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                   createStringAndAppendToFile(randomString);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 0, 5000);

    }

    private static void createStringAndAppendToFile(String randomString) throws IOException {

        final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        String uuidWithTimeStamp = String.format("%s: %s", formatter.format(new Date()), randomString);

        String file = "files/output.txt";
        Files.createDirectories(Paths.get("./files"));

        FileWriter fw = new FileWriter(file, false);
        fw.write(uuidWithTimeStamp);
        fw.close();
    }
}

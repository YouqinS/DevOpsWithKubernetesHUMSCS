package com.youqin.logoutput;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.UUID;

@Service
public class StatusService {

    final UUID uuid = UUID.randomUUID();

    public String getStatus() {
        final String randomString =  uuid.toString();
        final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        String uuidTimestamp = String.format("%s: %s", formatter.format(new Date()), randomString);
        String pingpong = readFile();
        return uuidTimestamp + "\n" + pingpong;
    }

    private static String readFile() {
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
}

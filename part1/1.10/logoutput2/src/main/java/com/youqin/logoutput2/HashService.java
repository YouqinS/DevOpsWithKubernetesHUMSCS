package com.youqin.logoutput2;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

@Service
public class HashService {

    public String getStatus() {
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

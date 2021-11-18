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

        try {
            writePingCountToFile(counter);
        } catch (IOException e) {
            e.printStackTrace();
        }

        counter += 1;
        return pong;
    }

    private void writePingCountToFile(int counter) throws IOException {
        String file = "files/output.txt";
        Files.createDirectories(Paths.get("./files"));

        String pingpongs = "Ping / Pongs: " + counter;

        System.out.println(pingpongs);

        FileWriter fw = new FileWriter(file, false);
        fw.write(pingpongs);
        fw.close();
    }
}

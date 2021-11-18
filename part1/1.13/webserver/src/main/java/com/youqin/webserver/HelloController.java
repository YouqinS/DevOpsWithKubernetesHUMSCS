package com.youqin.webserver;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

@RestController
public class HelloController {

    String directory = "./files";
    String picturePath = directory+"/picture.jpg";
    String pictureUrl = "https://picsum.photos/1200";

    @GetMapping(
            value = "/image",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public @ResponseBody  byte[] getImage() throws IOException {
        downloadPictureIfNeeded();
        InputStream is = new FileInputStream(new File(picturePath));
        return IOUtils.toByteArray(is);
    }

    private void downloadPictureIfNeeded() throws IOException {
        System.out.println("downloadPictureIfNeeded()");
        Files.createDirectories(Paths.get(directory));

        File pictureFile = new File(picturePath);
        boolean exists = pictureFile.exists();
        boolean shouldDownloadImage = !exists || ( (new Date().getTime() - pictureFile.lastModified()) >= 24 *60*60 *1000);
        System.out.println("should download="+shouldDownloadImage);
        if (shouldDownloadImage) {
            pictureFile.setLastModified(new Date().getTime());
            FileUtils.copyURLToFile(
                    new URL(pictureUrl),
                    pictureFile,
                    5000,
                    5000);
        }
    }

}
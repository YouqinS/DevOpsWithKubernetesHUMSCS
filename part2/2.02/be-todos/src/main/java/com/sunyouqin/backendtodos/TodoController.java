package com.sunyouqin.backendtodos;
import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TodoController {

    List<Todo> todos = new ArrayList<>();
    String directory = "./files";
    String picturePath = directory+"/picture.jpg";
    String pictureUrl = "https://picsum.photos/1200";

    @GetMapping("/todos")
    public List<Todo> getTodos() throws IOException {
        return todos;
    }

    @PostMapping("/todos")
    void addTodo(@RequestBody Todo todo) {
        todos.add(todo);
    }

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
        Files.createDirectories(Paths.get(directory));

        File pictureFile = new File(picturePath);
        boolean exists = pictureFile.exists();
        boolean shouldDownloadImage = !exists || ( (new Date().getTime() - pictureFile.lastModified()) >= 24 *60*60 *1000);
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
package com.sunyouqin.backendtodos;

import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;

@org.springframework.stereotype.Service
public class TodoService {
    String directory = "./files";
    String picturePath = directory+"/picture.jpg";
    String pictureUrl = "https://picsum.photos/1200";
    @Autowired
    private TodoRepository repository;

    public void addTodo(Todo todo) {
        repository.save(todo);
    }

    public List<Todo> getTodos() {

        return repository.findAll();
    }

    public void downloadPictureIfNeeded() throws IOException {
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

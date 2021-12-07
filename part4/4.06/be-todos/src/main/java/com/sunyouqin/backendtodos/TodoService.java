package com.sunyouqin.backendtodos;

import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.io.FileUtils;

@org.springframework.stereotype.Service
public class TodoService {
    String directory = "./files";
    String picturePath = directory+"/picture.jpg";
    String pictureUrl = "https://picsum.photos/1200";
    @Autowired
    private TodoRepository repository;

    public Todo addTodo(Todo todo) {
        String content = todo.getContent();
        if (content.length() > 140) {
            System.out.println("discarding \"" + content + "\" as it is over limit of 140 characters." );
            return null;
        }
        else {
            System.out.println("adding \"" + content + "\" to database as it is within the limit of 140 characters." );

           return repository.save(todo);
        }
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

    public Todo updateTodoStatus(Long id) {
        Optional<Todo> itemToUpdate = repository.findById(id);
        if (itemToUpdate.isPresent())  {
            itemToUpdate.get().setStatus("Done");
            return repository.save(itemToUpdate.get());
        } else {
           throw new RuntimeException("Todo not found with ID: " + id);
        }
    }
}

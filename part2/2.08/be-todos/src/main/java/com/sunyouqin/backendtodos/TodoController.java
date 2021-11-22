package com.sunyouqin.backendtodos;


import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
public class TodoController {
@Autowired
private TodoService service;
    @GetMapping("/")
    public String sayHi() {
        return "Greetings from to do app";
    }

    @GetMapping("/todo")
    public List<Todo> getTodos() {
        return service.getTodos();
    }

    @PostMapping("/todo")
    void addTodo(@RequestBody Todo todo) {
        service.addTodo(todo);
    }

    @GetMapping(
            value = "/image",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public @ResponseBody  byte[] getImage() throws IOException {
        String directory = "./files";
        String picturePath = directory+"/picture.jpg";
        service.downloadPictureIfNeeded();
        InputStream is = new FileInputStream(picturePath);
        return IOUtils.toByteArray(is);
    }
}
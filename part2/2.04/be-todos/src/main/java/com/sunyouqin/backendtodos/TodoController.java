package com.sunyouqin.backendtodos;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class TodoController {


    List<Todo> todos = new ArrayList<>();


    @GetMapping("/todos")
    public List<Todo> getTodos() throws IOException {
        return todos;
    }

    @PostMapping("/todos")
    void newEmployee(@RequestBody Todo todo) {
        todos.add(todo);
    }

}
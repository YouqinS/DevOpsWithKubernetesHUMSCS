package com.sunyouqin.backendtodos;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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

}
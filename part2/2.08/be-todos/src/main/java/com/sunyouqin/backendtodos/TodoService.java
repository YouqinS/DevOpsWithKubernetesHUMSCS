package com.sunyouqin.backendtodos;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
@org.springframework.stereotype.Service
public class TodoService {
    @Autowired
    private TodoRepository repository;

    public void addTodo(Todo todo) {
        repository.save(todo);
    }

    public List<Todo> getTodos() {

        return repository.findAll();
    }
}

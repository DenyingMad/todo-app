package com.devilpanda.todoapp.controller;

import com.devilpanda.todoapp.model.Task;
import com.devilpanda.todoapp.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/get-all-tasks")
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @PostMapping("/add-task")
    public Task createTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }
}


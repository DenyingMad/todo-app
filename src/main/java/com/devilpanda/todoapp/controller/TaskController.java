package com.devilpanda.todoapp.controller;

import com.devilpanda.todoapp.model.Task;
import com.devilpanda.todoapp.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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

    @PostMapping(value = "/add-task", consumes = MediaType.APPLICATION_JSON_VALUE)

    public Task createTask(@RequestBody Task task, @RequestParam("list_id") Long taskListId) {
        return taskService.createTask(task, taskListId);
    }
}


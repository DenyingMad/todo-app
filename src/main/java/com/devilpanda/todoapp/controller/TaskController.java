package com.devilpanda.todoapp.controller;

import com.devilpanda.todoapp.model.Task;
import com.devilpanda.todoapp.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/get-all-tasks")
    @ResponseBody
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @PostMapping("/add-task")
    public void createTask(@RequestBody Task task) {
        taskService.createTask(task);
    }
}


package com.devilpanda.todoapp.service;

import com.devilpanda.todoapp.model.Task;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TaskService {

    List<Task> getAllTasks();

    Task getTaskById(Long id);

    Task createTask(Task task, Long taskListId);

    boolean updateTask(Task task);
}

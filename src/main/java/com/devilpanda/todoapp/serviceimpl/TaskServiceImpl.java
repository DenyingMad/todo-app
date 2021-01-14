package com.devilpanda.todoapp.serviceimpl;

import com.devilpanda.todoapp.model.Task;
import com.devilpanda.todoapp.repository.TasksRepository;
import com.devilpanda.todoapp.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TasksRepository tasksRepository;

    @Override
    public List<Task> getAllTasks() {
        return tasksRepository.findAll();
    }

    @Override
    public Task getTaskById(Long id) {
        return null;
    }

    @Override
    public void createTask(Task task) {
        tasksRepository.saveAndFlush(task);
    }

    @Override
    public boolean updateTask(Task task) {
        return false;
    }
}

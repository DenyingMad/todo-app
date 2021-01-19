package com.devilpanda.todoapp.serviceimpl;

import com.devilpanda.todoapp.model.Task;
import com.devilpanda.todoapp.model.TaskList;
import com.devilpanda.todoapp.repository.TaskListRepository;
import com.devilpanda.todoapp.repository.TasksRepository;
import com.devilpanda.todoapp.service.TaskService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    private final TasksRepository tasksRepository;
    private final TaskListRepository taskListRepository;

    public TaskServiceImpl(TasksRepository tasksRepository, TaskListRepository taskListRepository) {
        this.tasksRepository = tasksRepository;
        this.taskListRepository = taskListRepository;
    }

    @Override
    public List<Task> getAllTasks() {
        return tasksRepository.findAll();
    }

    @Override
    public Task getTaskById(Long id) {
        return null;
    }

    @Override
    public Task createTask(Task task, Long taskListId) {
        TaskList taskList = taskListRepository.findById(taskListId).orElseThrow(() -> new EntityNotFoundException("No such task list"));
        task.setTaskList(taskList);
        return tasksRepository.saveAndFlush(task);
    }

    @Override
    public boolean updateTask(Task task) {
        return false;
    }
}

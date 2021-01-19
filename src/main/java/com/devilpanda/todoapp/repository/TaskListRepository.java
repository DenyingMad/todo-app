package com.devilpanda.todoapp.repository;

import com.devilpanda.todoapp.model.TaskList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskListRepository extends JpaRepository<TaskList, Long> {
}

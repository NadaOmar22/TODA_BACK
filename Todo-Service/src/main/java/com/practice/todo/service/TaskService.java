package com.practice.todo.service;

import com.practice.todo.model.Task;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TaskService {
    Task saveTask(Task task);
    Task getTask(Long id);
    void deleteTask(Long id);
    List<Task> searchTask(String title);
    List<Task> getUserTasks();
}

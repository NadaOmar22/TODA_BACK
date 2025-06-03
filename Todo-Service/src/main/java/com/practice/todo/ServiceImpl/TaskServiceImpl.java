package com.practice.todo.ServiceImpl;

import com.practice.todo.model.Task;
import com.practice.todo.model.TaskDetails;
import com.practice.todo.repo.TaskDetailsRepo;
import com.practice.todo.repo.TaskRepo;
import com.practice.todo.security.AuthConstants;
import com.practice.todo.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepo taskRepo;
    private final TaskDetailsRepo taskDetailsRepo;
    private final AuthConstants authConstants;

    @Override
    public Task saveTask(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Task object cannot be null");
        }

        try {
            TaskDetails details = task.getTaskDetails();
            task = taskRepo.save(task);
            if (details != null) {
                details.setTask(task);
                taskDetailsRepo.save(details);
            }
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to save task: " + e.getMessage(), e);
        }
        return task;
    }

    @Override
    public Task getTask(Long id) {
        try {
            return taskRepo.findById(id)
                    .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to retrieve task: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteTask(Long id) {
        try {
            if (!taskRepo.existsById(id)) {
                throw new RuntimeException("Task not found with id: " + id);
            }
            taskRepo.deleteById(id);
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to delete task: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Task> searchTask(String title) {
        try {
            return taskRepo.findByUserIdAndTitleContainingIgnoreCase(authConstants.getAUTH_USER_ID(), title);
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to search tasks: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Task> getUserTasks() {
        try {
            System.out.println("getUserTasks : authConstants.getAUTH_USER_ID() : " + authConstants.getAUTH_USER_ID());
            return taskRepo.findByUserId(authConstants.getAUTH_USER_ID());
        } catch (DataAccessException e) {
            throw new RuntimeException("Failed to retrieve user tasks: " + e.getMessage(), e);
        }
    }
}

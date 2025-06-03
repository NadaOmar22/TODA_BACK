package com.practice.todo.mapper;

import com.practice.todo.dto.TaskDTO;
import com.practice.todo.dto.TaskDetailsDTO;
import com.practice.todo.model.Task;
import com.practice.todo.model.TaskDetails;
import com.practice.todo.security.AuthConstants;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TaskMapper {
    AuthConstants authConstants;

    public Task mapToTask(TaskDTO taskDTO){
        Task task = new Task();
        task.setId(taskDTO.getId());
        task.setTitle(taskDTO.getTitle());
        System.out.println("authConstants.getAUTH_USER_ID() " + authConstants.getAUTH_USER_ID());
        task.setUserId(authConstants.getAUTH_USER_ID());
        task.setTaskDetails(mapToTaskDetails(taskDTO.getTaskDetails()));
        return task;
    }
    public TaskDetails mapToTaskDetails(TaskDetailsDTO taskDetailsDTO){
        TaskDetails taskDetails = new TaskDetails();
        taskDetails.setId(taskDetailsDTO.getId());
        taskDetails.setDescription(taskDetailsDTO.getDescription());
        taskDetails.setStatus(taskDetailsDTO.getStatus());
        taskDetails.setPriority(taskDetailsDTO.getPriority());
        taskDetails.setCreatedAt(taskDetailsDTO.getCreatedAt());
        taskDetails.setTask(taskDetails.getTask());
        return taskDetails;
    }

    public TaskDTO mapToTaskDTO(Task task){
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(task.getId());
        taskDTO.setTitle(task.getTitle());
        taskDTO.setUser(task.getUserId());
        taskDTO.setTaskDetails(mapToTaskDetailsDTO(task.getTaskDetails()));
        return taskDTO;
    }

    public TaskDetailsDTO mapToTaskDetailsDTO(TaskDetails taskDetails){
        TaskDetailsDTO taskDetailsDTO = new TaskDetailsDTO();
        taskDetailsDTO.setId(taskDetails.getId());
        taskDetailsDTO.setDescription(taskDetails.getDescription());
        taskDetailsDTO.setStatus(taskDetails.getStatus());
        taskDetailsDTO.setPriority(taskDetails.getPriority());
        taskDetailsDTO.setCreatedAt(taskDetails.getCreatedAt());
        return taskDetailsDTO;
    }
}

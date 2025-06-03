package com.practice.todo.controller;

import com.practice.todo.dto.TaskDTO;
import com.practice.todo.mapper.TaskMapper;
import com.practice.todo.model.Task;
import com.practice.todo.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tasks")
@Tag(name = "Task Controller", description = "APIs for managing tasks")
public class TaskController{

    private final TaskService taskService;
    private final TaskMapper taskMapper;

    @Autowired
    public TaskController(TaskService taskService, TaskMapper taskMapper) {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
    }

    @PostMapping("/create")
    @Operation(summary = "Create a new task")
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskDTO taskDTO) {
        Task task = taskMapper.mapToTask(taskDTO);
        task = taskService.saveTask(task);
        return ResponseEntity.ok(taskMapper.mapToTaskDTO(task));
    }

    @PutMapping("/update")
    @Operation(summary = "Update a new task")
    public ResponseEntity<TaskDTO>  updateTask(@RequestBody TaskDTO taskDTO) {
        Task task = taskMapper.mapToTask(taskDTO);
        task = taskService.saveTask(task);
        return ResponseEntity.ok(taskMapper.mapToTaskDTO(task));
    }

    @GetMapping("/fetch")
    @Operation(summary = "Get task by id")
    public ResponseEntity<TaskDTO> getTask(@RequestParam("id") Long id) {
        Task task = taskService.getTask(id);
        return ResponseEntity.ok(taskMapper.mapToTaskDTO(task));
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete task by id")
    public ResponseEntity<?>  deleteTask(@RequestParam("id") Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok(HttpEntity.EMPTY);
    }

    @GetMapping("/search")
    @Operation(summary = "Search By Title")
    public ResponseEntity<List<TaskDTO>> searchTask(@RequestParam("title") String title) {
        return ResponseEntity.ok(taskService.searchTask(title)
                .stream()
                .map(taskMapper::mapToTaskDTO)
                .collect(Collectors.toList()));
    }

    @GetMapping("/all")
    @Operation(summary = "Get All Tasks")
    public ResponseEntity<List<TaskDTO>> getUserTasks() {
        return ResponseEntity.ok( taskService.getUserTasks()
                .stream()
                .map(taskMapper::mapToTaskDTO)
                .collect(Collectors.toList()));
    }
}

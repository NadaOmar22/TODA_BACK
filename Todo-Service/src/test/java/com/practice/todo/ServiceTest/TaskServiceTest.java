package com.practice.todo.ServiceTest;

import com.practice.todo.ServiceImpl.TaskServiceImpl;
import com.practice.todo.model.Task;
import com.practice.todo.model.TaskDetails;
import com.practice.todo.repo.TaskDetailsRepo;
import com.practice.todo.repo.TaskRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @InjectMocks
    private TaskServiceImpl taskService;

    @Mock
    private TaskRepo taskRepo;

    @Mock
    private TaskDetailsRepo taskDetailsRepo;

    @Test
    void shouldCreateTaskWithCorrectTitle() {
        TaskDetails taskDetails = new TaskDetails();
        taskDetails.setDescription("DescTest");
        taskDetails.setStatus("pending");
        taskDetails.setPriority(2);

        Task task = new Task();
        task.setTaskDetails(taskDetails);
        task.setTitle("TestTask");

        when(taskRepo.save(any(Task.class))).thenReturn(task);

        Task createdTask = taskService.saveTask(task);

        taskDetails.setTask(createdTask);
        taskDetailsRepo.save(taskDetails);

        assertEquals("TestTask", createdTask.getTitle());
        assertEquals(createdTask.getId(), taskDetails.getTask().getId());
    }
}

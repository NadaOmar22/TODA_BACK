package com.practice.todo.repo;

import com.practice.todo.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepo extends JpaRepository<Task, Long> {
    List<Task> findByUserIdAndTitleContainingIgnoreCase(String userId, String title);
    List<Task> findByUserId(String userId);
}

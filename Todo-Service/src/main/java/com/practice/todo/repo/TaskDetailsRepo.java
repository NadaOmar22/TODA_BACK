package com.practice.todo.repo;

import com.practice.todo.model.TaskDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskDetailsRepo extends JpaRepository<TaskDetails, Long> {

}

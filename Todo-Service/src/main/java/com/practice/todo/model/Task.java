package com.practice.todo.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private String title;

    private String userId;

    @OneToOne(mappedBy = "task", cascade = CascadeType.ALL)
    private TaskDetails taskDetails;
}

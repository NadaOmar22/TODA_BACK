package com.practice.todo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Task Details Object")
public class TaskDetailsDTO {
    @Schema(description = "Task details ID")
    private Long id;

    @Schema(description = "Task description")
    @NotBlank
    private String description;

    @Schema(description = "Task Status")
    @NotBlank
    private String status;

    @Schema(description = "Task priority")
    @NotNull
    private Integer priority;

    @Schema(description = "Task date")
    private LocalDateTime createdAt;
}

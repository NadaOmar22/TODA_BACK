package com.practice.todo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Task Info")
public class TaskDTO {
    @Schema(description = "Task ID")
    private Long id;

    @Schema(description = "Title of the task", example = "Read Book")
    @NotBlank
    private String title;

    @Schema(description = "User ID")
    private String user;

    @Schema(description = "Task details object")
    @NotNull
    private TaskDetailsDTO taskDetails;
}

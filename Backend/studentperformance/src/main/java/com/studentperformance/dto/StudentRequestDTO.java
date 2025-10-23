package com.studentperformance.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentRequestDTO {
    
    @NotBlank(message = "Name is mandatory")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @Min(value = 5, message = "Age must be at least 5")
    @Max(value = 100, message = "Age must not exceed 100")
    private Integer age;

    @Pattern(regexp = "^(Male|Female|Other)$", message = "Gender must be Male, Female, or Other")
    private String gender;

    @DecimalMin(value = "0.0", message = "Attendance cannot be negative")
    @DecimalMax(value = "100.0", message = "Attendance cannot exceed 100%")
    private Double attendance;

    @DecimalMin(value = "0.0", message = "Average marks cannot be negative")
    @DecimalMax(value = "100.0", message = "Average marks cannot exceed 100")
    private Double avgMarks;
}
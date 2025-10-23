package com.studentperformance.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentResponseDTO {
    private Long id;
    private String name;
    private Integer age;
    private String gender;
    private Double attendance;
    private Double avgMarks;          // Average marks of the student
    private String finalPrediction;   // Prediction result from ML or business logic
}

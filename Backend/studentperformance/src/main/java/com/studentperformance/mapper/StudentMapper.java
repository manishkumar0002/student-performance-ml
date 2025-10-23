package com.studentperformance.mapper;

import com.studentperformance.dto.StudentResponseDTO;
import com.studentperformance.model.Student;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentMapper {
    
   
    public static StudentResponseDTO toDto(Student student) {
        if (student == null) {
            return null;
        }
        
        return StudentResponseDTO.builder()
                .id(student.getId())
                .name(student.getName())
                .age(student.getAge())
                .gender(student.getGender())
                .attendance(student.getAttendance())
                .avgMarks(student.getAvgMarks())
                .finalPrediction(student.getFinalPrediction())
                .build();
    }
    
    // Private constructor to prevent instantiation
    private StudentMapper() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }
}
package com.studentperformance.dto;

import jakarta.validation.constraints.NotNull;

public class ReferenceRequestDTO {

    @NotNull
    private Long studentId;

    @NotNull
    private Long subjectId;

    // Getters and Setters
    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }
}

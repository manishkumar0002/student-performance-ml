package com.studentperformance.service;

import com.studentperformance.dto.PerformanceRequestDTO;
import com.studentperformance.model.Performance;

import java.util.List;

public interface PerformanceService {
    Performance addPerformance(PerformanceRequestDTO dto);
    Performance updatePerformance(Long id, PerformanceRequestDTO dto);
    List<Performance> getPerformancesByStudentId(Long studentId);
    List<Performance> getAllPerformances();
    Performance getPerformanceById(Long id);
    void deletePerformance(Long id);

    // New method to calculate average marks
    Double getAverageMarksByStudentId(Long studentId);
    Double getAverageMarksBySubjectId(Long subjectId);

}

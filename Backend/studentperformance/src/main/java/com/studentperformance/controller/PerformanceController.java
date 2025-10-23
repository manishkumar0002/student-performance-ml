package com.studentperformance.controller;

import com.studentperformance.dto.PerformanceRequestDTO;
import com.studentperformance.model.Performance;
import com.studentperformance.service.PerformanceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/performances")
public class PerformanceController {

    private final PerformanceService performanceService;

    public PerformanceController(PerformanceService performanceService) {
        this.performanceService = performanceService;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> addPerformance(
            @Valid @RequestBody PerformanceRequestDTO dto) {
        Performance saved = performanceService.addPerformance(dto);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Performance recorded successfully");
        response.put("performanceId", saved.getId());
        response.put("studentId", saved.getStudent().getId());
        response.put("subjectId", saved.getSubject().getId());
        response.put("marks", saved.getMarks());
        response.put("term", saved.getTerm());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updatePerformance(
            @PathVariable Long id,
            @Valid @RequestBody PerformanceRequestDTO dto) {
        Performance updated = performanceService.updatePerformance(id, dto);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Performance updated successfully");
        response.put("id", updated.getId());
        response.put("marks", updated.getMarks());
        response.put("term", updated.getTerm());
        response.put("studentId", updated.getStudent().getId());
        response.put("subjectId", updated.getSubject().getId());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Performance>> getPerformancesByStudent(@PathVariable Long studentId) {
        List<Performance> performances = performanceService.getPerformancesByStudentId(studentId);
        return ResponseEntity.ok(performances);
    }

    @GetMapping
    public ResponseEntity<List<Performance>> getAllPerformances() {
        return ResponseEntity.ok(performanceService.getAllPerformances());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Performance> getPerformanceById(@PathVariable Long id) {
        return ResponseEntity.ok(performanceService.getPerformanceById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deletePerformance(@PathVariable Long id) {
        performanceService.deletePerformance(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Performance record deleted successfully");
        response.put("id", id.toString());
        return ResponseEntity.ok(response);
    }

    // ✅ New endpoint: get average marks of a student
    @GetMapping("/average/student/{studentId}")
    public ResponseEntity<Map<String, Object>> getAverageMarks(@PathVariable Long studentId) {
        Double average = performanceService.getAverageMarksByStudentId(studentId);

        Map<String, Object> response = new HashMap<>();
        response.put("studentId", studentId);
        response.put("averageMarks", average);

        return ResponseEntity.ok(response);
    }
    @GetMapping("/average/subject/{subjectId}")
    public ResponseEntity<Map<String, Object>> getAverageMarksBySubject(@PathVariable Long subjectId) {
        Double average = performanceService.getAverageMarksBySubjectId(subjectId);

        Map<String, Object> response = new HashMap<>();
        response.put("subjectId", subjectId);
        response.put("averageMarks", average);

        return ResponseEntity.ok(response);
    }

}

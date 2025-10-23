package com.studentperformance.controller;

import com.studentperformance.dto.StudentRequestDTO;
import com.studentperformance.dto.StudentResponseDTO;
import com.studentperformance.mapper.StudentMapper;
import com.studentperformance.model.Student;
import com.studentperformance.service.MLIntegrationService;
import com.studentperformance.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;
    private final MLIntegrationService mlIntegrationService;

    public StudentController(StudentService studentService, MLIntegrationService mlIntegrationService) {
        this.studentService = studentService;
        this.mlIntegrationService = mlIntegrationService;
    }

    @GetMapping
    public ResponseEntity<List<StudentResponseDTO>> getAllStudents() {
        List<StudentResponseDTO> dto = studentService.getAllStudents()
                .stream()
                .map(StudentMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> getStudentById(@PathVariable Long id) {
        Student s = studentService.getStudentById(id);
        return ResponseEntity.ok(StudentMapper.toDto(s));
    }

    @PostMapping
    public ResponseEntity<StudentResponseDTO> createStudent(@Valid @RequestBody StudentRequestDTO dto) {
        Student saved = studentService.createStudent(dto);
        return ResponseEntity.ok(StudentMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> updateStudent(@PathVariable Long id,  @Valid @RequestBody StudentRequestDTO dto) {
        Student updated = studentService.updateStudent(id, dto);
        return ResponseEntity.ok(StudentMapper.toDto(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        Map<String, String> resp = new HashMap<>();
        resp.put("message", "Student deleted successfully");
        return ResponseEntity.ok(resp);
    }

    @PostMapping("/{id}/predict")
    public ResponseEntity<Map<String, Object>> predictForStudent(@PathVariable Long id) {
        Student student = studentService.getStudentById(id);
        
        // Prepare features for ML service
        Map<String, Object> features = new HashMap<>();
        features.put("attendance", student.getAttendance() != null ? student.getAttendance() : 0.0);
        features.put("avg_marks", student.getAvgMarks() != null ? student.getAvgMarks() : 0.0);

        // Get prediction from ML service
        Map<String, Object> prediction = mlIntegrationService.predict(features);
        
        // Update student with prediction result
        if (prediction.containsKey("prediction")) {
            Object predictionValue = prediction.get("prediction");
            student.setFinalPrediction(predictionValue != null ? predictionValue.toString() : null);
            
            // Create DTO with existing data
            StudentRequestDTO updateDto = new StudentRequestDTO();
            updateDto.setName(student.getName());
            updateDto.setAge(student.getAge());
            updateDto.setGender(student.getGender());
            updateDto.setAttendance(student.getAttendance());
            updateDto.setAvgMarks(student.getAvgMarks());
            
            // Update the student
            student = studentService.updateStudent(id, updateDto);
        }
        
        // Prepare response
        Map<String, Object> resp = new HashMap<>();
        resp.put("prediction", prediction);
        resp.put("student", StudentMapper.toDto(student));
        return ResponseEntity.ok(resp);
    }
}
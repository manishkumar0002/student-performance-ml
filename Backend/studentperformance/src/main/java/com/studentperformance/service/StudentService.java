package com.studentperformance.service;

import com.studentperformance.dto.StudentRequestDTO;
import com.studentperformance.model.Student;

import java.util.List;

public interface StudentService {
    Student createStudent(StudentRequestDTO dto);
    Student updateStudent(Long id, StudentRequestDTO dto);
    Student getStudentById(Long id);
    List<Student> getAllStudents();
    void deleteStudent(Long id);
}
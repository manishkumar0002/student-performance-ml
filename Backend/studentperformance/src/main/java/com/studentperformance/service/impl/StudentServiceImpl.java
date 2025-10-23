package com.studentperformance.service.impl;

import com.studentperformance.dto.StudentRequestDTO;
import com.studentperformance.exception.ResourceNotFoundException;
import com.studentperformance.model.Student;
import com.studentperformance.repository.StudentRepository;
import com.studentperformance.service.StudentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    @Transactional
    public Student createStudent(StudentRequestDTO dto) {
        Student s = Student.builder()
                .name(dto.getName())
                .age(dto.getAge())
                .gender(dto.getGender())
                .attendance(dto.getAttendance())
                .avgMarks(dto.getAvgMarks())
                .build();
        return studentRepository.save(s);
    }

    @Override
    @Transactional
    public Student updateStudent(Long id, StudentRequestDTO dto) {
        Student existing = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
        
        existing.setName(dto.getName());
        existing.setAge(dto.getAge());
        existing.setGender(dto.getGender());
        existing.setAttendance(dto.getAttendance());
        existing.setAvgMarks(dto.getAvgMarks());
        // Note: finalPrediction is set separately via direct entity manipulation
        
        return studentRepository.save(existing);
    }

    @Override
    @Transactional(readOnly = true)
    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteStudent(Long id) {
        Student existing = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
        studentRepository.delete(existing);
    }
}
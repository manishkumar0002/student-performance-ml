package com.studentperformance.service.impl;

import com.studentperformance.dto.PerformanceRequestDTO;
import com.studentperformance.exception.ResourceNotFoundException;
import com.studentperformance.model.Performance;
import com.studentperformance.model.Student;
import com.studentperformance.model.Subject;
import com.studentperformance.repository.PerformanceRepository;
import com.studentperformance.repository.StudentRepository;
import com.studentperformance.repository.SubjectRepository;
import com.studentperformance.service.PerformanceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PerformanceServiceImpl implements PerformanceService {

    private final PerformanceRepository performanceRepository;
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;

    public PerformanceServiceImpl(
            PerformanceRepository performanceRepository,
            StudentRepository studentRepository,
            SubjectRepository subjectRepository) {
        this.performanceRepository = performanceRepository;
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
    }

    @Override
    @Transactional
    public Performance addPerformance(PerformanceRequestDTO dto) {
        Student student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", dto.getStudentId()));

        Subject subject = subjectRepository.findById(dto.getSubjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Subject", "id", dto.getSubjectId()));

        Performance performance = Performance.builder()
                .student(student)
                .subject(subject)
                .marks(dto.getMarks())
                .term(dto.getTerm())
                .build();

        return performanceRepository.save(performance);
    }

    @Override
    @Transactional
    public Performance updatePerformance(Long id, PerformanceRequestDTO dto) {
        Performance existing = performanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Performance", "id", id));

        existing.setMarks(dto.getMarks());
        existing.setTerm(dto.getTerm());
        // Optional: update student/subject if needed

        return performanceRepository.save(existing);
    }
    @Override
    @Transactional(readOnly = true)
    public Double getAverageMarksBySubjectId(Long subjectId) {
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new ResourceNotFoundException("Subject", "id", subjectId));

        List<Performance> performances = performanceRepository.findBySubject(subject);
        if (performances.isEmpty()) {
            return 0.0;
        }

        double total = performances.stream()
                .mapToDouble(Performance::getMarks)
                .sum();

        return total / performances.size();
    }


    @Override
    @Transactional(readOnly = true)
    public List<Performance> getPerformancesByStudentId(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", studentId));

        return performanceRepository.findByStudent(student);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Performance> getAllPerformances() {
        return performanceRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Performance getPerformanceById(Long id) {
        return performanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Performance", "id", id));
    }

    @Override
    @Transactional
    public void deletePerformance(Long id) {
        Performance existing = performanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Performance", "id", id));
        performanceRepository.delete(existing);
    }

    @Override
    @Transactional(readOnly = true)
    public Double getAverageMarksByStudentId(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", studentId));

        List<Performance> performances = performanceRepository.findByStudent(student);
        if (performances.isEmpty()) return 0.0;

        double total = performances.stream().mapToDouble(Performance::getMarks).sum();
        return total / performances.size();
    }
}

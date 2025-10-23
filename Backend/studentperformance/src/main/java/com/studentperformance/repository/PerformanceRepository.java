package com.studentperformance.repository;

import com.studentperformance.model.Performance;
import com.studentperformance.model.Student;
import com.studentperformance.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PerformanceRepository extends JpaRepository<Performance, Long> {
    
    // Find all performances for a student
    List<Performance> findByStudent(Student student);
    
    // Find all performances for a student by ID
    List<Performance> findByStudentId(Long studentId);
    
    // Find all performances for a subject
    List<Performance> findBySubject(Subject subject);
    
    // Find performances by term
    List<Performance> findByTerm(String term);
    
    // Find performances for a student in a specific term
    List<Performance> findByStudentAndTerm(Student student, String term);
    
    // Calculate average marks for a student
    @Query("SELECT AVG(p.marks) FROM Performance p WHERE p.student.id = :studentId")
    Double calculateAverageMarks(Long studentId);
    
    // Find top performers in a subject
    @Query("SELECT p FROM Performance p WHERE p.subject.id = :subjectId ORDER BY p.marks DESC")
    List<Performance> findTopPerformersInSubject(Long subjectId);
}
package com.studentperformance.repository;

import com.studentperformance.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    
    // Find students by name (case-insensitive)
    List<Student> findByNameContainingIgnoreCase(String name);
    
    // Find students by gender
    List<Student> findByGender(String gender);
    
    // Find students with attendance above a threshold
    @Query("SELECT s FROM Student s WHERE s.attendance >= :minAttendance")
    List<Student> findStudentsWithMinAttendance(Double minAttendance);
    
    // Find students with average marks above a threshold
    @Query("SELECT s FROM Student s WHERE s.avgMarks >= :minMarks")
    List<Student> findStudentsWithMinMarks(Double minMarks);
}
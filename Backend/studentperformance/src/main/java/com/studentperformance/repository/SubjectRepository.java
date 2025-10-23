package com.studentperformance.repository;

import com.studentperformance.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    
    // Find subject by code
    Optional<Subject> findByCode(String code);
    
    // Check if subject with code exists
    boolean existsByCode(String code);
    
    // Find subject by name (case-insensitive)
    Optional<Subject> findByNameIgnoreCase(String name);
}
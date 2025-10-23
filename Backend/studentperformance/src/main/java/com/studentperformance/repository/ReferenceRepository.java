package com.studentperformance.repository;

import com.studentperformance.model.Reference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReferenceRepository extends JpaRepository<Reference, Long> {
    // Optional: Custom query methods
    // List<Reference> findByStudentId(Long studentId);
    // List<Reference> findBySubjectId(Long subjectId);

}

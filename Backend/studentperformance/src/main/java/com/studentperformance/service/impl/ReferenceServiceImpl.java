package com.studentperformance.service.impl;

import com.studentperformance.dto.ReferenceRequestDTO;
import com.studentperformance.model.Reference;
import com.studentperformance.model.Student;
import com.studentperformance.model.Subject;
import com.studentperformance.repository.ReferenceRepository;
import com.studentperformance.repository.StudentRepository;
import com.studentperformance.repository.SubjectRepository;
import com.studentperformance.service.ReferenceService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReferenceServiceImpl implements ReferenceService {

    private final ReferenceRepository referenceRepository;
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;

    public ReferenceServiceImpl(ReferenceRepository referenceRepository,
                                StudentRepository studentRepository,
                                SubjectRepository subjectRepository) {
        this.referenceRepository = referenceRepository;
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
    }

    @Override
    public Reference createReference(ReferenceRequestDTO dto) {
        Student student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));
        Subject subject = subjectRepository.findById(dto.getSubjectId())
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        Reference reference = new Reference();
        reference.setStudent(student);
        reference.setSubject(subject);

        return referenceRepository.save(reference);
    }

    @Override
    public List<Reference> getAllReferences() {
        return referenceRepository.findAll();
    }

    @Override
    public void deleteReference(Long id) {
        referenceRepository.deleteById(id);
    }
}

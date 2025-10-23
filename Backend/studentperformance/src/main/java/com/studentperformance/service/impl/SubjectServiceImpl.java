package com.studentperformance.service.impl;

import com.studentperformance.exception.ResourceNotFoundException;
import com.studentperformance.model.Subject;
import com.studentperformance.repository.SubjectRepository;
import com.studentperformance.service.SubjectService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;

    public SubjectServiceImpl(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    @Override
    @Transactional
    public Subject createSubject(Subject subject) {
        // Check if subject code already exists
        if (subjectRepository.existsByCode(subject.getCode())) {
            throw new IllegalArgumentException("Subject with code " + subject.getCode() + " already exists");
        }
        return subjectRepository.save(subject);
    }

    @Override
    @Transactional
    public Subject updateSubject(Long id, Subject subject) {
        Subject existing = subjectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subject", "id", id));
        
        // Check if new code conflicts with another subject
        if (!existing.getCode().equals(subject.getCode()) && 
            subjectRepository.existsByCode(subject.getCode())) {
            throw new IllegalArgumentException("Subject with code " + subject.getCode() + " already exists");
        }
        
        existing.setName(subject.getName());
        existing.setCode(subject.getCode());
        return subjectRepository.save(existing);
    }

    @Override
    @Transactional(readOnly = true)
    public Subject getSubjectById(Long id) {
        return subjectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subject", "id", id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteSubject(Long id) {
        Subject existing = subjectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Subject", "id", id));
        subjectRepository.delete(existing);
    }
}

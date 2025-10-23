package com.studentperformance.service;

import com.studentperformance.model.Subject;

import java.util.List;

public interface SubjectService {
    Subject createSubject(Subject subject);
    Subject updateSubject(Long id, Subject subject);
    Subject getSubjectById(Long id);
    List<Subject> getAllSubjects();
    void deleteSubject(Long id);
}
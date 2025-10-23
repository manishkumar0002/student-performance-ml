package com.studentperformance.service;

import com.studentperformance.dto.ReferenceRequestDTO;
import com.studentperformance.model.Reference;

import java.util.List;

public interface ReferenceService {

    Reference createReference(ReferenceRequestDTO dto);

    List<Reference> getAllReferences();

    void deleteReference(Long id);
}

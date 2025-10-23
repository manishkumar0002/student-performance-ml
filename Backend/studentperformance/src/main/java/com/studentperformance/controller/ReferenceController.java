package com.studentperformance.controller;

import com.studentperformance.dto.ReferenceRequestDTO;
import com.studentperformance.dto.ReferenceResponseDTO;
import com.studentperformance.model.Reference;
import com.studentperformance.service.ReferenceService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/references")
public class ReferenceController {

    private final ReferenceService referenceService;

    public ReferenceController(ReferenceService referenceService) {
        this.referenceService = referenceService;
    }

    @PostMapping
    public ResponseEntity<ReferenceResponseDTO> createReference(@Valid @RequestBody ReferenceRequestDTO dto) {
        Reference created = referenceService.createReference(dto);
        ReferenceResponseDTO response = new ReferenceResponseDTO(
                created.getId(),
                created.getStudent().getId(),
                created.getSubject().getId()
        );
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ReferenceResponseDTO>> getAllReferences() {
        List<Reference> references = referenceService.getAllReferences();
        List<ReferenceResponseDTO> dtoList = references.stream()
                .map(ref -> new ReferenceResponseDTO(
                        ref.getId(),
                        ref.getStudent().getId(),
                        ref.getSubject().getId()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteReference(@PathVariable Long id) {
        referenceService.deleteReference(id);
        Map<String, String> resp = new HashMap<>();
        resp.put("message", "Reference deleted successfully");
        resp.put("id", id.toString());
        return ResponseEntity.ok(resp);
    }
}

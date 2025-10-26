package com.studentperformance.controller;

import com.studentperformance.model.InputData;
import com.studentperformance.service.MLIntegrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class PredictionController {

    @Autowired
    private MLIntegrationService mlIntegrationService;

    @PostMapping("/predict")
    public ResponseEntity<Map<String, Object>> predict(@RequestBody InputData inputData) {
        // ✅ Validate input
        if (inputData == null) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", "Invalid input data");
            return ResponseEntity.badRequest().body(error);
        }

        // ✅ Convert model input to Flask-compatible structure
        Map<String, Object> features = new HashMap<>();
        features.put("attendance", inputData.getAttendance());
        features.put("avgMarks", inputData.getAvgMarks());
        features.put("studyHours", inputData.getStudyHours());

        // ✅ Call ML Service
        Map<String, Object> result = mlIntegrationService.predict(features);

        return ResponseEntity.ok(result);
    }
}

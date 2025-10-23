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
        // ✅ Create payload expected by Flask: {"features": [ .. values .. ]}
        Map<String, Object> payload = new HashMap<>();
        payload.put("features", inputData.toFeatureArray());

        // ✅ Call ML service
        Map<String, Object> result = mlIntegrationService.predict(payload);

        return ResponseEntity.ok(result);
    }
}

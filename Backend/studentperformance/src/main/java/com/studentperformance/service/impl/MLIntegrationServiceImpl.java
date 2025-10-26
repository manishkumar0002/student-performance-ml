package com.studentperformance.service.impl;

import com.studentperformance.exception.MLServiceException;
import com.studentperformance.service.MLIntegrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class MLIntegrationServiceImpl implements MLIntegrationService {

    private static final Logger logger = LoggerFactory.getLogger(MLIntegrationServiceImpl.class);

    private final RestTemplate restTemplate;
    private final String mlServiceUrl;

    public MLIntegrationServiceImpl(RestTemplate restTemplate,
                                    @Value("${ml.service.url:http://localhost:5000/predict}") String mlServiceUrl) {
        this.restTemplate = restTemplate;
        this.mlServiceUrl = mlServiceUrl;
    }

    @Override
    public Map<String, Object> predict(Map<String, Object> features) {
        try {
            logger.info("🔗 Sending data to ML service at {}", mlServiceUrl);
            logger.debug("📤 Features: {}", features);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(features, headers);

            ResponseEntity<Map> response = restTemplate.postForEntity(mlServiceUrl, request, Map.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                logger.info("✅ Received response from ML service");
                return response.getBody();
            } else {
                throw new MLServiceException("ML service returned non-OK status: " + response.getStatusCode());
            }

        } catch (Exception ex) {
            logger.error("🚫 Error contacting ML service: {}", ex.getMessage(), ex);
            throw new MLServiceException("Error contacting ML service: " + ex.getMessage(), ex);
        }
    }
}

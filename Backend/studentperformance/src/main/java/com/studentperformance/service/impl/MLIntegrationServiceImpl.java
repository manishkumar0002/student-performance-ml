package com.studentperformance.service.impl;

import com.studentperformance.exception.MLServiceException;
import com.studentperformance.service.MLIntegrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class MLIntegrationServiceImpl implements MLIntegrationService {

    private static final Logger logger = LoggerFactory.getLogger(MLIntegrationServiceImpl.class);

    private final RestTemplate restTemplate;
    private final String mlServiceUrl;

    public MLIntegrationServiceImpl(
            RestTemplate restTemplate,
            @Value("${ml.service.url}") String mlServiceUrl
    ) {
        this.restTemplate = restTemplate;
        this.mlServiceUrl = mlServiceUrl;
    }

    @Override
    public Map<String, Object> predict(Map<String, Object> features) {
        try {
            logger.info("🔗 Calling ML service at: {}", mlServiceUrl);
            logger.debug("📤 Request payload: {}", features);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // ✅ Send payload to Flask
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(features, headers);

            ResponseEntity<Map> response = restTemplate.postForEntity(
                    mlServiceUrl,
                    request,
                    Map.class
            );

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                logger.info("✅ ML service responded successfully");
                logger.debug("📥 Response from ML: {}", response.getBody());
                return new HashMap<>(response.getBody());
            } else {
                logger.error("❌ ML service returned non-OK status: {}", response.getStatusCode());
                throw new MLServiceException("ML service returned non-OK status: " + response.getStatusCode());
            }

        } catch (RestClientException ex) {
            logger.error("🚫 Error calling ML service: {}", ex.getMessage(), ex);
            throw new MLServiceException("Failed to connect to ML service: " + ex.getMessage(), ex);
        } catch (Exception ex) {
            logger.error("💥 Unexpected error in ML service integration: {}", ex.getMessage(), ex);
            throw new MLServiceException("Unexpected error in ML prediction: " + ex.getMessage(), ex);
        }
    }
}

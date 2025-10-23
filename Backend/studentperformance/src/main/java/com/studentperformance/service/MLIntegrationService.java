package com.studentperformance.service;

import java.util.Map;

public interface MLIntegrationService {
    Map<String, Object> predict(Map<String, Object> features);
}

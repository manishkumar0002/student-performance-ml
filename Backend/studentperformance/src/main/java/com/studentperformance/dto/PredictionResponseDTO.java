package com.studentperformance.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PredictionResponseDTO {
    private Map<String, Object> rawPrediction;
    private String predictionLabel;
    private Double confidence;
    private String recommendation;
}
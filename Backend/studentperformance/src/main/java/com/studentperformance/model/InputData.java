package com.studentperformance.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InputData {

    private double attendance;
    private double avgMarks;
    private double studyHours;

    // ✅ Converts fields into list format expected by Flask
    public double[] toFeatureArray() {
        return new double[]{attendance, avgMarks, studyHours};
    }
}

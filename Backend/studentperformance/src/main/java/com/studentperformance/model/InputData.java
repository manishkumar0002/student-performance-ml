package com.studentperformance.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InputData {

    private double attendance;
    private double marks;
    private double subjectScore;
    private double studyHours;

    // ✅ Converts fields into array format expected by Flask
    public double[] toFeatureArray() {
        return new double[]{attendance, marks, subjectScore, studyHours};
    }
}

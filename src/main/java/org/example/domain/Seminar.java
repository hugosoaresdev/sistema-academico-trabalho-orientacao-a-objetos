package org.example.domain;

import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Seminar extends Assessment {

    public Seminar(Long assessmentID, String title, double value, double weight) {
        super(assessmentID, title, value, weight);
    }
}
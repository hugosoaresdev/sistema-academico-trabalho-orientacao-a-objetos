package org.example.domain;

import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Exam extends Assessment {

    public Exam(Long examID, String title, double maxWeight) {
        super(examID, title, 0.0, maxWeight);
    }
}
package org.example.domain;

/** US-2361 - Tipo de avaliação: Exame. */
public class ExamAssessment extends Assessment {

    public ExamAssessment() {
    }

    public ExamAssessment(double value, double weight) {
        super(value, weight);
    }

    @Override
    public String getType() {
        return "Exame";
    }
}
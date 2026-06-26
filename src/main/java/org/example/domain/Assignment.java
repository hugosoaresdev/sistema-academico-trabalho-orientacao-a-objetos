package org.example.domain;

/** US-2361 - Tipo de avaliação: Atribuição. */
public class Assignment extends Assessment {

    public Assignment() {
    }

    public Assignment(double value, double weight) {
        super(value, weight);
    }

    @Override
    public String getType() {
        return "Atribuição";
    }
}
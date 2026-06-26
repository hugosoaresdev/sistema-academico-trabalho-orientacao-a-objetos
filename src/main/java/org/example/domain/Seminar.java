package org.example.domain;

/** US-2361 - Tipo de avaliação: Seminário. */
public class Seminar extends Assessment {

    public Seminar() {
    }

    public Seminar(double value, double weight) {
        super(value, weight);
    }

    @Override
    public String getType() {
        return "Seminário";
    }
}
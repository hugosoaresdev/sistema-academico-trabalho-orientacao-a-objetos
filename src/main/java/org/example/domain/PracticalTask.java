package org.example.domain;

/** US-2361 - Tipo de avaliação: Tarefa Prática. */
public class PracticalTask extends Assessment {

    public PracticalTask() {
    }

    public PracticalTask(double value, double weight) {
        super(value, weight);
    }

    @Override
    public String getType() {
        return "Tarefa Prática";
    }
}
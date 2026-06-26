package org.example.domain;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * US-2361 - Superclasse comum a todos os tipos de avaliação.
 *
 * Guarda o que TODA avaliação tem em comum: um valor e um peso (AC3).
 * Os tipos concretos (Exame, Tarefa Prática, Seminário, Atribuição)
 * herdam desta classe e só informam qual é o seu tipo (getType).
 *
 * É abstrata de propósito: sempre criamos um tipo específico.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class Assessment {

    @Positive
    private double value;   // valor da avaliação (AC3)

    @Positive
    private double weight;  // peso da avaliação (AC3)

    /** Cada tipo concreto informa seu nome legível (ex: "Exame"). AC2. */
    public abstract String getType();
}
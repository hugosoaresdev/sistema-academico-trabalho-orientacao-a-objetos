package org.example.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Exam {

    private Long examID;

    @NotBlank
    private String title;

    @Positive
    private double maxWeight; //todo revisar: peso ou nota máxima, máximo peso?

}

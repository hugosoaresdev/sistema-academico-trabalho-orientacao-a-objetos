package org.example.domain;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class StudentTest {

    @Test
    void deveMatricularEstudante() {
        Student estudante = new Student("2000.1.04.010", "Ronaldo Fenômeno");

        // Testa o Lombok - getters e setters
        assertEquals("2000.1.04.010", estudante.getRegistrationNumber());
        assertEquals("Ronaldo Fenômeno", estudante.getName());
    }

    @Test
    void deveConsiderarAlunosComMesmoIDIguais(){
        Student student1 = new Student("2025.1.01.011", "Neymar Junior");
        Student student2 = new Student("2025.1.01.011", "Endrick Junior");

        assertEquals(student1, student2);
    }

    @Test
    void deveGerarMesmoCódigoHashParaAlunosComMesmoID() {
        Student student1 = new Student("2022.1.00.01", "Pelé");
        Student student2 = new Student("2022.1.00.01", "Rivaldo");

        assertEquals(student1.hashCode(), student2.hashCode());
    }
}

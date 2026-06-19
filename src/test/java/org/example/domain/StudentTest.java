package org.example.domain;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class StudentTest {

    private final Validator validator =
            Validation.buildDefaultValidatorFactory().getValidator();

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

    @Test
    void deveInvalidarAlunoSemMatricula() {

        Student student = new Student("", "Kaká");

        Set<ConstraintViolation<Student>> violations =
                validator.validate(student);

        assertFalse(violations.isEmpty());
    }

    @Test
    void deveInvalidarAlunoSemNome() {

        Student student = new Student("2025.1.08.111", "");

        Set<ConstraintViolation<Student>> violations =
                validator.validate(student);

        assertFalse(violations.isEmpty());
    }

    @Test
    void deveValidarAlunoComDadosValidos() {

        Student student =
                new Student("2006.1.08.010", "Vinicius Jr.");

        Set<ConstraintViolation<Student>> violations =
                validator.validate(student);

        assertTrue(violations.isEmpty());
    }
}

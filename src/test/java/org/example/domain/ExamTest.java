package org.example.domain;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ExamTest {

    private final Validator validator =
            Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void deveConsiderarExamsComMesmoIdIguais() {

        Exam exam1 =
                new Exam(1L, "P1", 10.0);

        Exam exam2 =
                new Exam(1L, "P2", 5.0);

        assertEquals(exam1, exam2);
    }

    @Test
    void deveGerarMesmoCodigoHashParaExamsComMesmoId() {

        Exam exam1 =
                new Exam(1L, "P1", 10.0);

        Exam exam2 =
                new Exam(1L, "P2", 5.0);

        assertEquals(exam1.hashCode(), exam2.hashCode());
    }

    @Test
    void deveConsiderarExamsComIdsDiferentesComoDiferentes() {

        Exam exam1 =
                new Exam(1L, "P1", 10.0);

        Exam exam2 =
                new Exam(2L, "P1", 10.0);

        assertNotEquals(exam1, exam2);
    }

    @Test
    void deveInvalidarExamSemTitulo() {

        Exam exam = new Exam(1L, "", 10.0);

        Set<ConstraintViolation<Exam>> violations =
                validator.validate(exam);

        assertFalse(violations.isEmpty());
    }

    @Test
    void deveInvalidarExamComPesoMenorOuIgualAZero() {

        Exam exam = new Exam(1L, "P1", 0);

        Set<ConstraintViolation<Exam>> violations =
                validator.validate(exam);

        assertFalse(violations.isEmpty());
    }

    @Test
    void deveValidarExamComDadosValidos() {

        Exam exam = new Exam(1L, "P1", 10.0);

        Set<ConstraintViolation<Exam>> violations =
                validator.validate(exam);

        assertTrue(violations.isEmpty());
    }
}

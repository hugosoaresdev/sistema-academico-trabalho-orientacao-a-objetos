package org.example.domain;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TeacherTest {
    private final Validator validator =
            Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void deveInvalidarProfessorSemNome() {

        Teacher teacher =
                new Teacher("", "POO", "123");

        Set<ConstraintViolation<Teacher>> violations =
                validator.validate(teacher);

        assertFalse(violations.isEmpty());
    }

    @Test
    void deveInvalidarProfessorSemDisciplina() {

        Teacher teacher =
                new Teacher("João", "", "123");

        Set<ConstraintViolation<Teacher>> violations =
                validator.validate(teacher);

        assertFalse(violations.isEmpty());
    }

    @Test
    void deveInvalidarProfessorSemMatricula() {

        Teacher teacher =
                new Teacher("João", "POO", "");

        Set<ConstraintViolation<Teacher>> violations =
                validator.validate(teacher);

        assertFalse(violations.isEmpty());
    }

    @Test
    void deveValidarProfessorComDadosValidos() {

        Teacher teacher =
                new Teacher("João", "POO", "123");

        Set<ConstraintViolation<Teacher>> violations =
                validator.validate(teacher);

        assertTrue(violations.isEmpty());
    }
}

package org.example.domain;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class TeacherTest {
    private final Validator validator =
            Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void deveConsiderarProfessoresComMesmaMatriculaIguais() {

        Teacher teacher1 =
                new Teacher("João", "POO", "123");

        Teacher teacher2 =
                new Teacher("Maria", "Banco de Dados", "123");

        assertEquals(teacher1, teacher2);
    }

    @Test
    void deveGerarMesmoCodigoHashParaProfessoresComMesmaMatricula() {

        Teacher teacher1 =
                new Teacher("João", "POO", "123");

        Teacher teacher2 =
                new Teacher("Maria", "Banco de Dados", "123");

        assertEquals(teacher1.hashCode(), teacher2.hashCode());
    }

    @Test
    void deveConsiderarProfessoresComMatriculasDiferentesComoDiferentes() {

        Teacher teacher1 =
                new Teacher("João", "POO", "123");

        Teacher teacher2 =
                new Teacher("João", "POO", "456");

        assertNotEquals(teacher1, teacher2);
    }

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

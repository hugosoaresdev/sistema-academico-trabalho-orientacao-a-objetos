package org.example.domain;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ClassroomTest {

    private final Validator validator =
            Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void deveConsiderarClassroomsComMesmoIdIguais() {

        Classroom classroom1 = new Classroom();
        classroom1.setClassroomID(1L);

        Classroom classroom2 = new Classroom();
        classroom2.setClassroomID(1L);

        assertEquals(classroom1, classroom2);
    }

    @Test
    void deveGerarMesmoCodigoHashParaClassroomsComMesmoId() {

        Classroom classroom1 = new Classroom();
        classroom1.setClassroomID(1L);

        Classroom classroom2 = new Classroom();
        classroom2.setClassroomID(1L);

        assertEquals(classroom1.hashCode(), classroom2.hashCode());
    }

    @Test
    void deveConsiderarClassroomsComIdsDiferentesComoDiferentes() {

        Classroom classroom1 = new Classroom();
        classroom1.setClassroomID(1L);

        Classroom classroom2 = new Classroom();
        classroom2.setClassroomID(2L);

        assertNotEquals(classroom1, classroom2);
    }

    @Test
    void deveInvalidarClassroomSemNome() {

        Validator validator =
                Validation.buildDefaultValidatorFactory().getValidator();

        Teacher teacher = new Teacher();

        Classroom classroom = new Classroom();
        classroom.setClassroomID(1L);
        classroom.setClassroomName("");
        classroom.setClassroomTeacher(teacher);

        Set<ConstraintViolation<Classroom>> violations =
                validator.validate(classroom);

        assertFalse(violations.isEmpty());
    }

    @Test
    void deveInvalidarClassroomSemProfessor() {

        Validator validator =
                Validation.buildDefaultValidatorFactory().getValidator();

        Classroom classroom = new Classroom();
        classroom.setClassroomID(1L);
        classroom.setClassroomName("Orientação a Objetos");
        classroom.setClassroomTeacher(null);

        Set<ConstraintViolation<Classroom>> violations =
                validator.validate(classroom);

        assertFalse(violations.isEmpty());
    }

    @Test
    void deveValidarClassroomComDadosValidos() {

        Teacher teacher = new Teacher(
                "Maria Silva",
                "Orientação a Objetos",
                "2025.1.01.001"
        );

        Classroom classroom = new Classroom();
        classroom.setClassroomID(1L);
        classroom.setClassroomName("Orientação a Objetos");
        classroom.setClassroomTeacher(teacher);

        Set<ConstraintViolation<Classroom>> violations =
                validator.validate(classroom);

        assertTrue(violations.isEmpty());
    }
}

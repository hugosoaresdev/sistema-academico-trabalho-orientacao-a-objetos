package org.example.domain;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClassroomTest {

    private final Validator validator =
            Validation.buildDefaultValidatorFactory().getValidator();

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

        Validator validator =
                Validation.buildDefaultValidatorFactory().getValidator();

        Teacher teacher = new Teacher();

        Classroom classroom = new Classroom();
        classroom.setClassroomID(1L);
        classroom.setClassroomName("Orientação a Objetos");
        classroom.setClassroomTeacher(teacher);

        Set<ConstraintViolation<Classroom>> violations =
                validator.validate(classroom);

        assertTrue(violations.isEmpty());
    }
}

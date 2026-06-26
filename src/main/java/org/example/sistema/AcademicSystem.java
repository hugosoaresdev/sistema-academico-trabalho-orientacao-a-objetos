package org.example.sistema;

import org.example.domain.Classroom;
import org.example.exception.AcademicSystemException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AcademicSystem {

    private static AcademicSystem instance;

    private final List<Classroom> classrooms = new ArrayList<>();

    private AcademicSystem() {
    }

    public static synchronized AcademicSystem getInstance() {
        if (instance == null) {
            instance = new AcademicSystem();
        }
        return instance;
    }

    public void registerClassroom(Classroom classroom) {
        boolean idAlreadyExists = classrooms.stream()
                .anyMatch(c -> c.getClassroomID().equals(classroom.getClassroomID()));

        if (idAlreadyExists) {
            throw new AcademicSystemException(
                    "Já existe uma turma registrada com o código: " + classroom.getClassroomID());
        }

        classrooms.add(classroom);
    }

    public Optional<Classroom> findClassroomById(Long classroomID) {
        return classrooms.stream()
                .filter(c -> c.getClassroomID().equals(classroomID))
                .findFirst();
    }

    public List<Classroom> getClassrooms() {
        return classrooms;
    }
}
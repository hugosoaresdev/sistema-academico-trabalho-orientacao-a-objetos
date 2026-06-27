package org.example.sistema;

import org.example.domain.Classroom;
import org.example.exception.AcademicSystemException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.example.repository.PersistenceType;

public class AcademicSystem {

    // TUS-2398: tipo de persistência atualmente configurado.
    // Começa em TXT por padrão; o admin pode trocar via US-2372.
    private PersistenceType persistenceType = PersistenceType.TXT;
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
    //Persistencia de arquivo
    public PersistenceType getPersistenceType() {
        return persistenceType;
    }

    public void setPersistenceType(PersistenceType persistenceType) {
        this.persistenceType = persistenceType;
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
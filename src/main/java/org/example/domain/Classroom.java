package org.example.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "classroomID")

public class Classroom { //todo revisar: nome da classe estranho

    @NotNull
    private Long classroomID;

    @NotBlank
    private String classroomName;

    @NotNull
    @Valid
    private Teacher classroomTeacher;

    private List<Student> classroomListOfStudents = new ArrayList<>();
    private List<Exam> classroomListOfExams = new ArrayList<>();

    // --- Avaliações (US-2361 / TUS-2397) -------------------------------
    // Lista polimórfica: guarda QUALQUER tipo de avaliação (Exam, Seminar,
    // PracticalAssignment, Assignment), porque todas "são uma" Assessment.
    // Foi ADICIONADA ao lado da lista antiga de Exam para não quebrar o
    // código que já usava classroomListOfExams / adicionaNaListaDeProvas.
    @Valid
    private List<Assessment> classroomListOfAssessments = new ArrayList<>();

    public void adicionaNaListaDeProvas(Exam exam){
        classroomListOfExams.add(exam);
    }

    /**
     * Adiciona uma avaliação (de qualquer tipo) à turma.
     * US-2361 - AC1 e AC7: a avaliação registrada passa a aparecer na turma.
     */
    public void addAssessment(Assessment assessment) {
        classroomListOfAssessments.add(assessment);
    }

}
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

    public void adicionaNaListaDeProvas(Exam exam){
        classroomListOfExams.add(exam);
    }

}

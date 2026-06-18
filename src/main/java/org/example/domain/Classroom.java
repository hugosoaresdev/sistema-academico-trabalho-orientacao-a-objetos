package org.example.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor

public class Classroom {

    private Long classroomID;
    private String classroomName;
    private Teacher classroomTeacher;

    private List<Student> classroomListOfStudents = new ArrayList<>();
    private List<Exam> classroomListOfExams = new ArrayList<>();

    public void adicionaNaListaDeProvas(Exam exam){
        classroomListOfExams.add(exam);
    }

}

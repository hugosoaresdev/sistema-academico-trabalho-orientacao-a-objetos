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

    private Long classID;
    private String className;
    private Teacher classTeacher;

    private List<Student> classListOfStudents = new ArrayList<>();
    private List<Exam> classListOfExams = new ArrayList<>();

    public void adicionaNaListaDeProvas(Exam exam){
        classListOfExams.add(exam);
    }

}

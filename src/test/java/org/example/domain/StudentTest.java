package org.example.domain;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StudentTest {

    @Test
    void matricularEstudante() {
        Student estudante = new Student("2025.1.08.010", "Zacarias Silva");

        // Testa o Lombok - getters e setters
        assertEquals("2025.1.08.010", estudante.getStudentID());
        assertEquals("Zacarias Silva", estudante.getName());
    }
}

package org.example.service;

import org.example.domain.Classroom;
import org.example.domain.Teacher;
import org.example.exception.AcademicSystemException;
import org.example.sistema.AcademicSystem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * TUS-2401 - Teste do comportamento do ClassService.
 *
 * Cobre os critérios de aceitação:
 * - ClassService deve registrar uma classe válida.
 * - A turma registrada deve ser armazenada no AcademicSystem.
 * - Dados de classe inválidos devem gerar AcademicSystemException.
 *
 * ATENÇÃO: AcademicSystem é um Singleton, então a lista de turmas
 * persiste entre os testes desta classe (e de qualquer outra classe
 * de teste que rode na mesma JVM). Por isso, cada teste aqui usa um
 * classroomID diferente — assim um teste não atrapalha o outro por
 * causa de "turma duplicada".
 */
class ClassServiceTest {

    private ClassService classService;

    @BeforeEach
    void setUp() {
        // Uma instância nova de ClassService por teste é barato
        // (ela só guarda o Validator), então não tem problema recriar.
        classService = new ClassService();
    }

    /**
     * Monta um Teacher válido só pra preencher o Classroom nos testes
     * que não estão testando o Teacher em si.
     */
    private Teacher umProfessorValido() {
        return new Teacher("Maria Silva", "Matemática", "12345");
    }

    @Test
    void deveRegistrarUmaTurmaValida() {
        Classroom classroom = new Classroom();
        classroom.setClassroomID(1001L);
        classroom.setClassroomName("Turma de Cálculo I");
        classroom.setClassroomTeacher(umProfessorValido());

        // Se não lançar exceção, o registro foi bem-sucedido.
        assertDoesNotThrow(() -> classService.registerClassroom(classroom));
    }

    @Test
    void turmaRegistradaDeveSerArmazenadaNoAcademicSystem() {
        Classroom classroom = new Classroom();
        classroom.setClassroomID(1002L);
        classroom.setClassroomName("Turma de Física I");
        classroom.setClassroomTeacher(umProfessorValido());

        classService.registerClassroom(classroom);

        // Confirma que a turma realmente foi para dentro do AcademicSystem,
        // e não só "passou pela validação sem erro".
        boolean turmaFoiArmazenada = AcademicSystem.getInstance()
                .getClassrooms()
                .stream()
                .anyMatch(c -> c.getClassroomID().equals(1002L));

        assertTrue(turmaFoiArmazenada, "A turma deveria estar na lista do AcademicSystem");
    }

    @Test
    void deveLancarExcecaoQuandoCodigoDaTurmaForNulo() {
        Classroom classroom = new Classroom();
        classroom.setClassroomID(null); // @NotNull no domínio
        classroom.setClassroomName("Turma sem código");
        classroom.setClassroomTeacher(umProfessorValido());

        assertThrows(AcademicSystemException.class,
                () -> classService.registerClassroom(classroom));
    }

    @Test
    void deveLancarExcecaoQuandoTituloDaTurmaForEmBranco() {
        Classroom classroom = new Classroom();
        classroom.setClassroomID(1003L);
        classroom.setClassroomName(""); // @NotBlank no domínio
        classroom.setClassroomTeacher(umProfessorValido());

        assertThrows(AcademicSystemException.class,
                () -> classService.registerClassroom(classroom));
    }

    @Test
    void deveLancarExcecaoQuandoProfessorForNulo() {
        Classroom classroom = new Classroom();
        classroom.setClassroomID(1004L);
        classroom.setClassroomName("Turma sem professor");
        classroom.setClassroomTeacher(null); // @NotNull no domínio

        assertThrows(AcademicSystemException.class,
                () -> classService.registerClassroom(classroom));
    }

    @Test
    void deveLancarExcecaoQuandoCodigoDaTurmaJaExistir() {
        Classroom primeira = new Classroom();
        primeira.setClassroomID(1005L);
        primeira.setClassroomName("Turma Original");
        primeira.setClassroomTeacher(umProfessorValido());
        classService.registerClassroom(primeira);

        Classroom duplicada = new Classroom();
        duplicada.setClassroomID(1005L); // mesmo ID da anterior
        duplicada.setClassroomName("Turma Duplicada");
        duplicada.setClassroomTeacher(umProfessorValido());

        assertThrows(AcademicSystemException.class,
                () -> classService.registerClassroom(duplicada));
    }
}
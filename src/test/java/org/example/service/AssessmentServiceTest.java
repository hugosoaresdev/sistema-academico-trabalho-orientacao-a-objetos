package org.example.service;

import org.example.domain.Classroom;
import org.example.domain.Teacher;
import org.example.exception.AcademicSystemException;
import org.example.sistema.AcademicSystem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * TUS-2402 - Teste do comportamento do AssessmentService.
 *
 * Cobre:
 * - registrar avaliação numa turma existente,
 * - a avaliação fica armazenada na turma selecionada,
 * - tipo inválido não adiciona avaliação,
 * - código de turma inexistente não adiciona avaliação.
 *
 * ATENÇÃO: AcademicSystem é Singleton, então a lista de turmas
 * persiste entre os testes. Por isso cada teste usa um classroomID
 * diferente, pra um não atrapalhar o outro.
 */
class AssessmentServiceTest {

    private AssessmentService assessmentService;

    @BeforeEach
    void setUp() {
        assessmentService = new AssessmentService();
    }

    /** Cria e registra uma turma de apoio (pra ter onde adicionar avaliações). */
    private Classroom criarTurmaRegistrada(Long id) {
        Classroom classroom = new Classroom();
        classroom.setClassroomID(id);
        classroom.setClassroomName("Turma " + id);
        classroom.setClassroomTeacher(new Teacher("Maria Silva", "Matemática", "12345"));
        AcademicSystem.getInstance().registerClassroom(classroom);
        return classroom;
    }

    @Test
    void deveRegistrarAvaliacaoEmTurmaExistente() {
        criarTurmaRegistrada(2001L);

        // tipo 1 = Exame, valor 8.0, peso 0.4
        assertDoesNotThrow(() ->
                assessmentService.registerAssessment(2001L, 1, 8.0, 0.4));
    }

    @Test
    void avaliacaoRegistradaDeveSerArmazenadaNaTurma() {
        Classroom classroom = criarTurmaRegistrada(2002L);

        assessmentService.registerAssessment(2002L, 2, 7.5, 0.3);

        // A avaliação deve estar na lista de avaliações da turma.
        assertEquals(1, classroom.getClassroomListOfAssessments().size());
        assertEquals("Tarefa Prática",
                classroom.getClassroomListOfAssessments().getFirst().getType());
    }

    @Test
    void tipoInvalidoNaoDeveAdicionarAvaliacao() {
        Classroom classroom = criarTurmaRegistrada(2003L);

        // tipo 9 não existe → deve lançar exceção
        assertThrows(AcademicSystemException.class,
                () -> assessmentService.registerAssessment(2003L, 9, 8.0, 0.4));

        // E nada deve ter sido adicionado à turma.
        assertTrue(classroom.getClassroomListOfAssessments().isEmpty());
    }

    @Test
    void turmaInexistenteNaoDeveAdicionarAvaliacao() {
        // código 9999 não foi registrado → deve lançar exceção
        assertThrows(AcademicSystemException.class,
                () -> assessmentService.registerAssessment(9999L, 1, 8.0, 0.4));
    }

    @Test
    void dadosInvalidosNaoDevemAdicionarAvaliacao() {
        Classroom classroom = criarTurmaRegistrada(2004L);

        // valor negativo viola @Positive → deve lançar exceção
        assertThrows(AcademicSystemException.class,
                () -> assessmentService.registerAssessment(2004L, 1, -5.0, 0.4));

        assertTrue(classroom.getClassroomListOfAssessments().isEmpty());
    }
}
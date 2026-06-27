package org.example.service;

import org.example.domain.Classroom;
import org.example.domain.ExamAssessment;
import org.example.domain.PracticalTask;
import org.example.domain.Teacher;
import org.example.repository.PersistenceType;
import org.example.sistema.AcademicSystem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * US-2388 / TUS-2404 - Testes da geração de relatórios (ReportService).
 *
 * Cobre as duas histórias num arquivo só:
 * - relatório resumido inclui código, título, tipo, valor e peso (US-2388),
 * - relatório de peso calcula o total corretamente (US-2388),
 * - relatório de configuração mostra o tipo de persistência (US-2388),
 * - e cada método do ReportService gera o relatório esperado (TUS-2404).
 *
 * ATENÇÃO: AcademicSystem é Singleton e não permite remover turmas,
 * então cada teste usa IDs/títulos únicos e verifica a presença do seu
 * próprio conteúdo no relatório (não depende da quantidade total de turmas).
 */
class ReportServiceTest {

    private ReportService reportService;

    @BeforeEach
    void setUp() {
        reportService = new ReportService();
    }

    /** Registra uma turma com avaliações, usando ID único por teste. */
    private Classroom registrarTurmaComAvaliacoes(Long id, String nome) {
        Classroom classroom = new Classroom();
        classroom.setClassroomID(id);
        classroom.setClassroomName(nome);
        classroom.setClassroomTeacher(new Teacher("Prof", "Disc", "000"));
        classroom.adicionaAvaliacao(new ExamAssessment(8.0, 0.6));
        classroom.adicionaAvaliacao(new PracticalTask(7.0, 0.4));

        if (AcademicSystem.getInstance().findClassroomById(id).isEmpty()) {
            AcademicSystem.getInstance().registerClassroom(classroom);
        }
        return classroom;
    }

    @Test
    void relatorioResumoDeveConterDadosDaTurmaEDaAvaliacao() {
        registrarTurmaComAvaliacoes(8001L, "Turma Resumo");

        String relatorio = reportService.generateClassAssessmentSummaryReport();

        // US-2388: deve conter código, título, tipo, valor e peso.
        assertTrue(relatorio.contains("8001"), "Deve conter o código da turma");
        assertTrue(relatorio.contains("Turma Resumo"), "Deve conter o título da turma");
        assertTrue(relatorio.contains("Exame"), "Deve conter o tipo de avaliação");
        assertTrue(relatorio.contains("8.0"), "Deve conter o valor da avaliação");
        assertTrue(relatorio.contains("0.6"), "Deve conter o peso da avaliação");
    }

    @Test
    void relatorioDePesoDeveCalcularPesoTotalCorretamente() {
        // 0.6 + 0.4 = 1.0 → composição válida.
        registrarTurmaComAvaliacoes(8002L, "Turma Peso Valido");

        String relatorio = reportService.generateAssessmentWeightReport();

        assertTrue(relatorio.contains("Turma Peso Valido"), "Deve listar a turma");
        // O peso total deve ser 1.0 e a composição deve ser indicada como válida.
        assertTrue(relatorio.contains("1.0"), "Deve mostrar o peso total calculado");
        assertTrue(relatorio.contains("VÁLIDA"), "Peso total 1.0 deve ser válido");
    }

    @Test
    void relatorioDePesoDeveIndicarComposicaoInvalida() {
        // Turma com uma única avaliação de peso 0.5 → total 0.5 ≠ 1.0 → inválida.
        Classroom classroom = new Classroom();
        classroom.setClassroomID(8003L);
        classroom.setClassroomName("Turma Peso Invalido");
        classroom.setClassroomTeacher(new Teacher("Prof", "Disc", "000"));
        classroom.adicionaAvaliacao(new ExamAssessment(5.0, 0.5));
        if (AcademicSystem.getInstance().findClassroomById(8003L).isEmpty()) {
            AcademicSystem.getInstance().registerClassroom(classroom);
        }

        String relatorio = reportService.generateAssessmentWeightReport();

        assertTrue(relatorio.contains("Turma Peso Invalido"), "Deve listar a turma");
        assertTrue(relatorio.contains("INVÁLIDA"), "Peso total 0.5 deve ser inválido");
    }

    @Test
    void relatorioDeConfiguracaoDeveMostrarTipoSelecionado() {
        // Configura JSON e confirma que o relatório reflete isso.
        AcademicSystem.getInstance().setPersistenceType(PersistenceType.JSON);

        String relatorio = reportService.generatePersistenceConfigurationReport();

        assertTrue(relatorio.contains("JSON"), "Deve mostrar o tipo configurado (JSON)");

        // Reseta para o padrão pra não afetar outros testes.
        AcademicSystem.getInstance().setPersistenceType(PersistenceType.TXT);
    }
}
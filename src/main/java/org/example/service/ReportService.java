package org.example.service;

import org.example.repository.PersistenceType;
import org.example.sistema.AcademicSystem;
import org.example.domain.Assessment;
import org.example.domain.Classroom;

import java.util.List;

/**
 * TUS-2399 - Serviço de geração de relatórios.
 *
 * Centraliza a lógica de geração de relatórios, isolando-a do menu e
 * do controlador. Cada tipo de relatório é um método aqui.
 */
public class ReportService {

    /**
     * TUS-2377 - Relatório de configuração de persistência.
     *
     * Monta um texto indicando qual tipo de persistência está
     * atualmente configurado (TXT, XML ou JSON). Não modifica nenhum
     * dado acadêmico (AC5): apenas lê a configuração ativa.
     *
     * @return o texto do relatório, pronto para ser exibido
     */
    public String generatePersistenceConfigurationReport() {
        PersistenceType type = AcademicSystem.getInstance().getPersistenceType();

        return """
               === Relatório de Configuração de Persistência ===
               Tipo de persistência configurado: %s
               =================================================
               """.formatted(type);
    }

    /**
     * US-2375 - Relatório resumido de avaliações da turma.
     *
     * Lista todas as turmas registradas (AC1), cada uma com código e
     * título, e suas avaliações com tipo, valor e peso (AC2). Turmas
     * sem avaliações também aparecem (AC3). Sem turmas, retorna um
     * relatório válido e vazio (AC4). Não modifica dados (AC7).
     */
    public String generateClassAssessmentSummaryReport() {
        List<Classroom> classrooms = AcademicSystem.getInstance().getClassrooms();

        StringBuilder report = new StringBuilder();
        report.append("=== Relatório Resumido de Avaliações da Turma ===\n");

        if (classrooms.isEmpty()) {
            report.append("(nenhuma turma registrada)\n");
        } else {
            for (Classroom classroom : classrooms) {
                report.append("\nTurma: ").append(classroom.getClassroomID())
                        .append(" - ").append(classroom.getClassroomName()).append("\n");

                List<Assessment> assessments = classroom.getClassroomListOfAssessments();
                if (assessments.isEmpty()) {
                    report.append("  (sem avaliações)\n");
                } else {
                    for (Assessment a : assessments) {
                        report.append("  - ").append(a.getType())
                                .append(" | valor=").append(a.getValue())
                                .append(" | peso=").append(a.getWeight()).append("\n");
                    }
                }
            }
        }

        report.append("=================================================\n");
        return report.toString();
    }

    /**
     * US-2376 - Relatório de peso das avaliações.
     *
     * Lista cada turma registrada (AC1) e calcula o peso total das suas
     * avaliações (AC2). Indica se a composição é válida (peso total = 1.0,
     * AC3) ou inválida (diferente de 1.0, AC4). Turma sem avaliações tem
     * peso total 0.0 (AC5). Não modifica dados (AC8).
     */
    public String generateAssessmentWeightReport() {
        List<Classroom> classrooms = AcademicSystem.getInstance().getClassrooms();

        StringBuilder report = new StringBuilder();
        report.append("=== Relatório de Peso das Avaliações ===\n");

        if (classrooms.isEmpty()) {
            report.append("(nenhuma turma registrada)\n");
        } else {
            for (Classroom classroom : classrooms) {
                double pesoTotal = 0.0;
                for (Assessment a : classroom.getClassroomListOfAssessments()) {
                    pesoTotal += a.getWeight();
                }

                report.append("\nTurma: ").append(classroom.getClassroomID())
                        .append(" - ").append(classroom.getClassroomName()).append("\n");
                report.append("  Peso total: ").append(pesoTotal).append("\n");

                // Comparação com tolerância: somar doubles (ex: 0.6 + 0.4)
                // pode dar 0.9999999 em vez de 1.0 exato, então usamos uma
                // pequena margem em vez de == direto.
                if (Math.abs(pesoTotal - 1.0) < 0.0001) {
                    report.append("  Composição: VÁLIDA\n");
                } else {
                    report.append("  Composição: INVÁLIDA\n");
                }
            }
        }

        report.append("========================================\n");
        return report.toString();
    }
}
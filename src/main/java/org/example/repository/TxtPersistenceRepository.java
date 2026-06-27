package org.example.repository;

import org.example.domain.Assessment;
import org.example.domain.Classroom;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;
import java.io.BufferedWriter;

/**
 * TUS-2362 - Implementação de persistência em arquivo TXT.
 *
 * Escreve as turmas registradas e suas avaliações num arquivo texto
 * simples e legível. Cada turma traz pelo menos código e título (AC4),
 * e cada avaliação traz tipo, valor e peso (AC3/AC4).
 *
 * Usa apenas FileWriter (já vem no Java, sem dependência externa).
 */
public class TxtPersistenceRepository implements PersistenceRepository {

    // TUS-2393: logger para registrar as operações de persistência.
    // Usa java.util.logging (sem dependência externa) até a TUS-2390
    // definir o framework de logging oficial do projeto.
    private static final Logger LOGGER =
            Logger.getLogger(TxtPersistenceRepository.class.getName());

    // Nome do arquivo gerado. Mantido simples por enquanto.
    private static final String FILE_NAME = "academic_data.txt";

    @Override
    public void save(List<Classroom> classrooms) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {

            writer.write("=== DADOS ACADÊMICOS ===\n\n");

            for (Classroom classroom : classrooms) {
                writer.write("Turma: " + classroom.getClassroomID()
                        + " - " + classroom.getClassroomName() + "\n");

                List<Assessment> assessments = classroom.getClassroomListOfAssessments();

                if (assessments.isEmpty()) {
                    writer.write("  (sem avaliações)\n");
                } else {
                    for (Assessment a : assessments) {
                        writer.write("  Avaliação: " + a.getType()
                                + " | valor=" + a.getValue()
                                + " | peso=" + a.getWeight() + "\n");
                    }
                }
                writer.write("\n");
            }
        }

        // TUS-2393: registra a operação só depois do arquivo ser fechado
        // com sucesso (o fechamento é onde os dados são gravados no disco).
        LOGGER.info("Persistência executada: tipo=TXT, arquivo=" + FILE_NAME
                + ", turmas=" + classrooms.size());
    }
    public String getFileName() {
        return FILE_NAME;
    }
}
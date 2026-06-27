package org.example.service;

import org.example.domain.Classroom;
import org.example.domain.ExamAssessment;
import org.example.domain.Teacher;
import org.example.repository.PersistenceType;
import org.example.sistema.AcademicSystem;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

/**
 * TUS-2403 - Testes do comportamento do PersistenceService.
 *
 * Cobre:
 * - salvar usando o repositório TXT padrão,
 * - trocar o tipo de persistência para XML,
 * - trocar o tipo de persistência para JSON,
 * - os arquivos salvos devem conter dados da turma.
 *
 * ATENÇÃO: o tipo de persistência fica no AcademicSystem (Singleton),
 * então cada teste seta o tipo que precisa e o @AfterEach reseta para
 * TXT e remove os arquivos gerados, pra um teste não afetar o outro.
 */
class PersistenceServiceTest {

    private PersistenceService persistenceService;

    @BeforeEach
    void setUp() {
        persistenceService = new PersistenceService();

        // Garante que existe pelo menos uma turma com avaliação para salvar.
        Classroom classroom = new Classroom();
        classroom.setClassroomID(700L);
        classroom.setClassroomName("Turma Persistencia");
        classroom.setClassroomTeacher(new Teacher("Bia", "Química", "777"));
        classroom.adicionaAvaliacao(new ExamAssessment(9.0, 0.6));

        // Só registra se ainda não existir (evita duplicidade entre testes).
        if (AcademicSystem.getInstance().findClassroomById(700L).isEmpty()) {
            AcademicSystem.getInstance().registerClassroom(classroom);
        }
    }

    @AfterEach
    void limpar() throws IOException {
        // Reseta o tipo para o padrão e remove os arquivos gerados.
        persistenceService.setPersistenceType(PersistenceType.TXT);
        Files.deleteIfExists(Path.of("academic_data.txt"));
        Files.deleteIfExists(Path.of("academic_data.xml"));
        Files.deleteIfExists(Path.of("academic_data.json"));
    }

    @Test
    void deveSalvarUsandoRepositorioTxtPadrao() throws IOException {
        // O padrão é TXT, mas setamos explicitamente para deixar claro.
        persistenceService.setPersistenceType(PersistenceType.TXT);
        persistenceService.save();

        Path arquivo = Path.of("academic_data.txt");
        assertTrue(Files.exists(arquivo), "Deveria ter gerado o arquivo TXT");
        assertTrue(Files.readString(arquivo).contains("700"),
                "O TXT deveria conter os dados da turma");
    }

    @Test
    void deveTrocarTipoParaXmlESalvar() throws IOException {
        persistenceService.setPersistenceType(PersistenceType.XML);

        // Confirma que o tipo foi realmente alterado.
        assertEquals(PersistenceType.XML, persistenceService.getPersistenceType());

        persistenceService.save();

        Path arquivo = Path.of("academic_data.xml");
        assertTrue(Files.exists(arquivo), "Deveria ter gerado o arquivo XML");
        assertTrue(Files.readString(arquivo).contains("700"),
                "O XML deveria conter os dados da turma");
    }

    @Test
    void deveTrocarTipoParaJsonESalvar() throws IOException {
        persistenceService.setPersistenceType(PersistenceType.JSON);

        assertEquals(PersistenceType.JSON, persistenceService.getPersistenceType());

        persistenceService.save();

        Path arquivo = Path.of("academic_data.json");
        assertTrue(Files.exists(arquivo), "Deveria ter gerado o arquivo JSON");
        assertTrue(Files.readString(arquivo).contains("700"),
                "O JSON deveria conter os dados da turma");
    }
}
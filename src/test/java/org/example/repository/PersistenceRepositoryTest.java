package org.example.repository;

import org.example.domain.Classroom;
import org.example.domain.ExamAssessment;
import org.example.domain.Teacher;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * US-2389 - Testes dos repositórios de persistência (TXT, XML, JSON).
 *
 * Cada teste cria turmas com avaliações, chama o save() do repositório
 * e confirma que o arquivo foi gerado e contém os dados esperados
 * (dados de turma e de avaliação).
 *
 * Os arquivos gerados são removidos depois de cada teste (@AfterEach)
 * para não deixar lixo no projeto.
 */
class PersistenceRepositoryTest {

    private final TxtPersistenceRepository txtRepo = new TxtPersistenceRepository();
    private final XmlPersistenceRepository xmlRepo = new XmlPersistenceRepository();
    private final JsonPersistenceRepository jsonRepo = new JsonPersistenceRepository();

    /** Monta uma lista de turmas com avaliações, pra usar nos testes. */
    private List<Classroom> dadosDeTeste() {
        Classroom classroom = new Classroom();
        classroom.setClassroomID(500L);
        classroom.setClassroomName("Turma de Teste");
        classroom.setClassroomTeacher(new Teacher("Ana", "História", "999"));
        classroom.adicionaAvaliacao(new ExamAssessment(8.0, 0.5));
        return List.of(classroom);
    }

    /** Remove os arquivos gerados depois de cada teste. */
    @AfterEach
    void limpar() throws IOException {
        Files.deleteIfExists(Path.of(txtRepo.getFileName()));
        Files.deleteIfExists(Path.of(xmlRepo.getFileName()));
        Files.deleteIfExists(Path.of(jsonRepo.getFileName()));
    }

    @Test
    void repositorioTxtDeveGerarArquivoTxtComDados() throws IOException {
        txtRepo.save(dadosDeTeste());

        Path arquivo = Path.of(txtRepo.getFileName());
        assertTrue(Files.exists(arquivo), "O arquivo TXT deveria existir");

        String conteudo = Files.readString(arquivo);
        // Dados de turma e de avaliação devem aparecer no arquivo.
        assertTrue(conteudo.contains("500"), "Deveria conter o código da turma");
        assertTrue(conteudo.contains("Turma de Teste"), "Deveria conter o título da turma");
        assertTrue(conteudo.contains("Exame"), "Deveria conter o tipo de avaliação");
    }

    @Test
    void repositorioXmlDeveGerarArquivoXmlComDados() throws IOException {
        xmlRepo.save(dadosDeTeste());

        Path arquivo = Path.of(xmlRepo.getFileName());
        assertTrue(Files.exists(arquivo), "O arquivo XML deveria existir");

        String conteudo = Files.readString(arquivo);
        assertTrue(conteudo.contains("500"), "Deveria conter o código da turma");
        assertTrue(conteudo.contains("Turma de Teste"), "Deveria conter o título da turma");
        assertTrue(conteudo.contains("Exame"), "Deveria conter o tipo de avaliação");
    }

    @Test
    void repositorioJsonDeveGerarArquivoJsonComDados() throws IOException {
        jsonRepo.save(dadosDeTeste());

        Path arquivo = Path.of(jsonRepo.getFileName());
        assertTrue(Files.exists(arquivo), "O arquivo JSON deveria existir");

        String conteudo = Files.readString(arquivo);
        assertTrue(conteudo.contains("500"), "Deveria conter o código da turma");
        assertTrue(conteudo.contains("Turma de Teste"), "Deveria conter o título da turma");
        assertTrue(conteudo.contains("Exame"), "Deveria conter o tipo de avaliação");
    }
}
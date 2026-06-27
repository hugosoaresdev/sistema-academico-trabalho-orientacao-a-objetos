package org.example.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.example.domain.Classroom;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

/**
 * US-2374 - Persistência em arquivo JSON.
 *
 * Gera um arquivo JSON (AC1) contendo todas as turmas registradas (AC2),
 * cada uma com código e título (AC3) e suas avaliações com tipo, valor
 * e peso (AC4). É uma implementação da abstração PersistenceRepository
 * (AC5), igual ao TXT.
 *
 * Não altera o modelo de domínio (AC7): só serializa os dados.
 * Não interfere nas persistências TXT/XML existentes (AC8/AC9): é uma
 * classe separada.
 */
public class JsonPersistenceRepository implements PersistenceRepository {

    private static final Logger LOGGER =
            Logger.getLogger(JsonPersistenceRepository.class.getName());

    private static final String FILE_NAME = "academic_data.json";

    // O ObjectMapper é quem converte os objetos Java em JSON.
    // SerializationFeature.INDENT_OUTPUT deixa o JSON "formatado"
    // (com quebras de linha e indentação), mais fácil de ler.
    private final ObjectMapper objectMapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT);

    @Override
    public void save(List<Classroom> classrooms) throws IOException {
        objectMapper.writeValue(new File(FILE_NAME), classrooms);

        // TUS-2393: registra a operação de persistência, incluindo o tipo.
        LOGGER.info("Persistência executada: tipo=JSON, arquivo=" + FILE_NAME
                + ", turmas=" + classrooms.size());
    }


    /** Expõe o nome do arquivo (útil pros testes da US-2389). */
    public String getFileName() {
        return FILE_NAME;
    }
}
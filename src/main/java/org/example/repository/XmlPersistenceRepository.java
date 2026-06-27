package org.example.repository;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.example.domain.Classroom;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

/**
 * US-2373 - Persistência em arquivo XML.
 *
 * Gera um arquivo XML (AC1) contendo todas as turmas registradas (AC2),
 * cada uma com código e título (AC3) e suas avaliações com tipo, valor
 * e peso (AC4). É uma implementação da abstração PersistenceRepository
 * (AC5), igual ao TXT e ao JSON.
 *
 * Não altera o modelo de domínio (AC7): só serializa os dados.
 * Não interfere nas persistências TXT/JSON existentes (AC8/AC9): é uma
 * classe separada.
 */
public class XmlPersistenceRepository implements PersistenceRepository {

    private static final Logger LOGGER =
            Logger.getLogger(XmlPersistenceRepository.class.getName());
    private static final String FILE_NAME = "academic_data.xml";

    // O XmlMapper é o equivalente do ObjectMapper, só que gera XML
    // em vez de JSON. INDENT_OUTPUT deixa o XML formatado/legível.
    private final XmlMapper xmlMapper = (XmlMapper) new XmlMapper()
            .enable(SerializationFeature.INDENT_OUTPUT);

    @Override
    public void save(List<Classroom> classrooms) throws IOException {
        xmlMapper.writeValue(new File(FILE_NAME), classrooms);

        // TUS-2393: registra a operação de persistência, incluindo o tipo.
        LOGGER.info("Persistência executada: tipo=XML, arquivo=" + FILE_NAME
                + ", turmas=" + classrooms.size());
    }

    /** Expõe o nome do arquivo (útil pros testes da US-2389). */
    public String getFileName() {
        return FILE_NAME;
    }
}
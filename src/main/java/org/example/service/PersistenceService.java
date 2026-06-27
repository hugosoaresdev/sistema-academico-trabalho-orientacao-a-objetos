package org.example.service;

import org.example.repository.JsonPersistenceRepository;
import org.example.repository.PersistenceRepository;
import org.example.repository.PersistenceType;
import org.example.repository.TxtPersistenceRepository;
import org.example.repository.XmlPersistenceRepository;
import org.example.sistema.AcademicSystem;

import java.io.IOException;

/**
 * TUS-2398 - Serviço de persistência.
 *
 * Centraliza as decisões de persistência, isolando-as do menu/controlador:
 *  - sabe qual tipo de persistência está configurado,
 *  - permite trocar o tipo (US-2372),
 *  - e salva os dados usando o repositório correspondente ao tipo ativo.
 *
 * O tipo ativo é guardado no AcademicSystem (Singleton), então a escolha
 * do admin sobrevive entre as operações.
 */
public class PersistenceService {

    /** US-2372 - troca o tipo de persistência ativo. */
    public void setPersistenceType(PersistenceType type) {
        AcademicSystem.getInstance().setPersistenceType(type);
    }

    /** Retorna o tipo de persistência atualmente configurado (TUS-2377). */
    public PersistenceType getPersistenceType() {
        return AcademicSystem.getInstance().getPersistenceType();
    }

    /**
     * Salva os dados acadêmicos usando o repositório do tipo configurado.
     * É aqui que o tipo ativo é traduzido na implementação concreta.
     */
    public void save() throws IOException {
        PersistenceRepository repository = resolverRepositorio();
        repository.save(AcademicSystem.getInstance().getClassrooms());
    }

    /**
     * Escolhe a implementação de repositório de acordo com o tipo ativo.
     * Tipo novo no enum → só adicionar um case aqui.
     */
    private PersistenceRepository resolverRepositorio() {
        return switch (getPersistenceType()) {
            case TXT -> new TxtPersistenceRepository();
            case XML -> new XmlPersistenceRepository();
            case JSON -> new JsonPersistenceRepository();
        };
    }
}
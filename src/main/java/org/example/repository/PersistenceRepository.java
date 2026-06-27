package org.example.repository;

import org.example.domain.Classroom;

import java.util.List;

/**
 * TUS-2362 - Abstração de repositório de persistência (AC5).
 *
 * Define o contrato para salvar os dados acadêmicos, independente do
 * formato do arquivo. Cada formato (TXT, XML, JSON) terá sua própria
 * implementação concreta desta interface.
 *
 * O modelo de domínio não é alterado por nenhuma implementação (AC6):
 * o repositório apenas LÊ as turmas e escreve no arquivo.
 */
public interface PersistenceRepository {

    /**
     * Salva a lista de turmas (com suas avaliações) num arquivo.
     *
     * @param classrooms turmas a serem persistidas
     * @throws java.io.IOException se houver erro ao escrever o arquivo
     */
    void save(List<Classroom> classrooms) throws java.io.IOException;
}
package org.example.service;

import org.example.repository.PersistenceType;
import org.example.sistema.AcademicSystem;

/**
 * TUS-2399 - Serviço de geração de relatórios.
 *
 * Centraliza a lógica de geração de relatórios, isolando-a do menu e
 * do controlador. Cada tipo de relatório é um método aqui.
 *
 * Por enquanto cobre o relatório de configuração de persistência
 * (TUS-2377). Os relatórios de avaliação (US-2375/US-2376) entram
 * como novos métodos quando forem implementados.
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
}
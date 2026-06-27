package org.example.repository;

/**
 * TUS-2398 / US-2372 - Tipos de persistência suportados.
 *
 * Usar um enum (em vez de String) evita erro de digitação e deixa
 * claro, em tempo de compilação, quais são os únicos formatos válidos.
 */
public enum PersistenceType {
    TXT,
    XML,
    JSON
}

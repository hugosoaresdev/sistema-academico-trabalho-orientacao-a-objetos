package org.example.service;

import org.example.security.Authenticate;
import org.example.security.User;

import java.io.IOException;

/**
 * TUS-2414 - Serviço de autenticação.
 *
 * Concentra a regra de negócio de autenticar (delegando à classe
 * Authenticate, que lê o repositório de usuários TXT). Mantém a GUI
 * e os controladores livres dessa lógica.
 */
public class AuthenticationService {

    private final Authenticate authenticate = new Authenticate();

    /**
     * Autentica o usuário pelas credenciais.
     *
     * @return o usuário autenticado
     * @throws IOException se houver erro ao ler o repositório de usuários.
     *         (AuthenticationExceptionAcademic é propagada se as credenciais
     *          forem inválidas — sem ser alterada aqui.)
     */
    public User authenticate(String email, String password) throws IOException {
        return authenticate.login(email, password);
    }
}
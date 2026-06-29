package org.example.controller;

import org.example.security.User;
import org.example.service.AuthenticationService;

import java.io.IOException;

/**
 * TUS-2414 - Controlador de autenticação.
 *
 * AC2: delega as solicitações de autenticação ao AuthenticationService.
 * AC5: retorna o usuário autenticado em caso de sucesso.
 * AC6: propaga as exceções de autenticação sem alterar o tratamento existente.
 * AC10: reutilizável por futuras telas JavaFX e outras camadas.
 */
public class AuthenticationController {

    private final AuthenticationService authenticationService = new AuthenticationService();

    public User authenticate(String email, String password) throws IOException {
        return authenticationService.authenticate(email, password);
    }
}
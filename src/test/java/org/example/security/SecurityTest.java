package org.example.security;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class SecurityTest {

    private Authenticate authService;

    @BeforeEach
    void setUp() {
        authService = new Authenticate();
    }

    @Test
    void deveAutenticarAdministradorComDadosValidos() throws IOException {

        // Baseado no arquivo real: admin@sistema.com;admin123;ADMIN
        User user = authService.login("admin@sistema.com", "admin123");

        assertNotNull(user);
        assertEquals(Role.ADMIN, user.getRole());
    }

    @Test
    void deveAutenticarProfessorComDadosValidos() throws IOException {

        // Baseado no arquivo real: professor@sistema.com;prof123;TEACHER
        User user = authService.login("professor@sistema.com", "prof123");

        assertNotNull(user);
        assertEquals(Role.TEACHER, user.getRole());
    }

    @Test
    void deveLancarExcecaoQuandoSenhaEstiverIncorreta() {

        AuthenticationException exception = assertThrows(AuthenticationException.class, () -> {
            authService.login("admin@sistema.com", "senha_errada");
        });

        assertEquals("Erro: senha incorreta.", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoEmailNaoForEncontrado() {

        AuthenticationException exception = assertThrows(AuthenticationException.class, () -> {
            authService.login("inexistente@sistema.com", "admin123");
        });

        assertEquals("Erro: email não encontrado.", exception.getMessage());
    }

    @Test
    void devePermitirAcessoQuandoUsuarioPossuiRoleExataExigida() {

        User admin = new User("admin@sistema.com", Role.ADMIN);

        assertDoesNotThrow(() -> authService.checkAuthorize(admin, Role.ADMIN));
    }

    @Test
    void devePermitirAcessoQuandoUsuarioPossuiRoleSuperiorAExigida() {

        User admin = new User("admin@sistema.com", Role.ADMIN);

        assertDoesNotThrow(() -> authService.checkAuthorize(admin, Role.TEACHER));
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioPossuiRoleInferiorAExigida() {

        User professor = new User("professor@sistema.com", Role.TEACHER);

        AuthorizationException exception = assertThrows(AuthorizationException.class, () -> {
            authService.checkAuthorize(professor, Role.ADMIN);
        });

        assertTrue(exception.getMessage().contains("ACESSO NEGADO: operação exige role ADMIN"));
    }
}
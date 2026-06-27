package org.example.security;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.slf4j.LoggerFactory;

public class SecurityTest {

    private Authenticate authService;
    private Logger logger;
    private ListAppender<ILoggingEvent> listAppender;

    @BeforeEach
    void setUp() {
        authService = new Authenticate();

        logger = (Logger) LoggerFactory.getLogger(Authenticate.class);

        listAppender = new ListAppender<>();
        listAppender.start();

        logger.addAppender(listAppender);
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

        AuthenticationExceptionAcademic exception = assertThrows(AuthenticationExceptionAcademic.class, () -> {
            authService.login("admin@sistema.com", "senha_errada");
        });

        assertEquals("Erro: senha incorreta.", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoEmailNaoForEncontrado() {

        AuthenticationExceptionAcademic exception = assertThrows(AuthenticationExceptionAcademic.class, () -> {
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

        AuthorizationExceptionAcademic exception = assertThrows(AuthorizationExceptionAcademic.class, () -> {
            authService.checkAuthorize(professor, Role.ADMIN);
        });

        assertTrue(exception.getMessage().contains("ACESSO NEGADO: operação exige role ADMIN"));
    }

    @Test
    void deveRegistrarLogQuandoLoginForRealizadoComSucesso() throws IOException {

        authService.login("admin@sistema.com", "admin123");

        boolean encontrouLog = listAppender.list.stream()
                .anyMatch(event ->
                        event.getFormattedMessage().contains("Usuário autenticado com sucesso: admin@sistema.com"));

        assertTrue(encontrouLog);
    }

    @Test
    void deveRegistrarLogQuandoSenhaEstiverIncorreta() {

        assertThrows(AuthenticationExceptionAcademic.class, () -> {
            authService.login("admin@sistema.com", "senha_errada");
        });

        boolean encontrouLog = listAppender.list.stream()
                .anyMatch(event ->
                        event.getFormattedMessage().contains(
                                "Falha na autenticação: senha incorreta para o usuário: admin@sistema.com"));

        assertTrue(encontrouLog);
    }

    @Test
    void deveRegistrarLogQuandoAcessoForNegado() {

        User professor = new User("professor@sistema.com", Role.TEACHER);

        assertThrows(AuthorizationExceptionAcademic.class, () -> {
            authService.checkAuthorize(professor, Role.ADMIN);
        });

        boolean encontrouLog = listAppender.list.stream()
                .anyMatch(event ->
                        event.getFormattedMessage().contains(
                                "Acesso negado para o usuário professor@sistema.com"));

        assertTrue(encontrouLog);
    }
}
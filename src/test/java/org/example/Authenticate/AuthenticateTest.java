package org.example.Authenticate;

import org.example.domain.Teacher;
import org.example.Authenticate.SecurityException;
import org.example.Authenticate.AuthenticationException;
import org.example.Authenticate.AuthorizationException;

import org.junit.jupiter.api.Test;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class AuthenticateTest {

    private final Authenticate auth = new Authenticate();

    @Test
    void testeCapturaExcecaoComoOComponentePrincipalFaria() {
        // AC6: Simulação de captura amigável e segura no componente principal
        try {
            // Passamos o utilizador correto "Hugo" mas com a password errada para forçar a falha
            auth.login("Hugo", "senha_errada");
            fail("Deveria ter lançado uma exceção antes de chegar aqui!");

        } catch (SecurityException | IOException e) {
            System.out.println("Mensagem exibida com seguranca: " + e.getMessage());

            // AC5: Verifica se a mensagem bate com o padrão definido com vírgula e se é o tipo correto (Authentication)
            assertEquals("Erro, senha incorreta!", e.getMessage());
            assertTrue(e instanceof AuthenticationException);
        }
    }

    @Test
    public void deveAutenticarComSucessoQuandoCredenciaisCorretas() throws IOException {
        // AC8: Garante o comportamento existente inalterado para logins válidos
        boolean resultado = auth.login("Hugo", "Ghou");
        assertTrue(resultado, "O login deveria retornar true para credenciais validas.");
    }

    @Test
    public void deveLancarExcecaoQuandoSenhaEstiverIncorreta() {
        // AC1: Senha incorreta lança AuthenticationException
        AuthenticationException excecao = assertThrows(AuthenticationException.class, () -> {
            auth.login("Hugo", "senha_errada");
        });

        assertEquals("Erro, senha incorreta!", excecao.getMessage());
    }

    @Test
    public void deveLancarExcecaoQuandoEmailNaoExistir() {
        // AC1: E-mail não localizado no TXT lança AuthenticationException
        AuthenticationException excecao = assertThrows(AuthenticationException.class, () -> {
            auth.login("email_fantasma@email.com", "123456");
        });

        assertEquals("Erro, email incorreto!", excecao.getMessage());
    }

    // ========================================================
    // TESTES DE AUTORIZAÇÃO E HIERARQUIA (AC2, AC3 e AC4)
    // ========================================================

    @Test
    public void devePermitirProfessorAcederAoNichoDeEstudante() {
        Teacher professorDummy = new Teacher();
        // Professor (1) acede a Student (0) -> Permitido, não deve disparar nenhuma exceção
        assertDoesNotThrow(() -> {
            auth.checkAuthorize(professorDummy, Authenticate.Role.STUDENT);
        });
    }

    @Test
    public void deveBarrarProfessorSeTentarAcederAoNichoDeAdmin() {
        Teacher professorDummy = new Teacher();
        // AC2: Professor (1) acede a Admin (2) -> Lança obrigatoriamente AuthorizationException
        AuthorizationException excecao = assertThrows(AuthorizationException.class, () -> {
            auth.checkAuthorize(professorDummy, Authenticate.Role.ADMIN);
        });

        assertEquals("ACESSO NEGADO: usuario nao tem permissao", excecao.getMessage());
    }
}
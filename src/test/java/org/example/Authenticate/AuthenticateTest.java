package org.example.Authenticate;

import org.example.domain.Teacher;
import org.junit.jupiter.api.Test;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class AuthenticateTest {

    @Test
    public void deveAutenticarComSucessoQuandoCredenciaisCorretas() throws IOException {
        Authenticate auth = new Authenticate();

        // Testa o cenário feliz (mude para um e-mail/senha reais do seu usuarios.txt)
        boolean resultado = auth.login("Hugo", "Ghou");

        assertTrue(resultado, "O login deveria retornar true para credenciais válidas.");
    }

    @Test
    public void deveLancarExcecaoQuandoSenhaEstiverIncorreta() {
        Authenticate auth = new Authenticate();

        // Passamos um e-mail correto, mas a senha errada
        // O assertThrows confirma se o sistema "explodiu" a exceção certa
        AuthException excecao = assertThrows(AuthException.class, () -> {
            auth.login("Alisson", "VemHexa");
        });

        // Confirma se a mensagem de erro da exceção está correta
        assertEquals("Erro, senha incorreta!", excecao.getMessage());
    }

    @Test
    public void deveLancarExcecaoQuandoEmailNaoExistir() {
        Authenticate auth = new Authenticate();

        // Passamos um e-mail que sabidamente não está no arquivo TXT
        AuthException excecao = assertThrows(AuthException.class, () -> {
            auth.login("email_fantasma@email.com", "123456");
        });

        assertEquals("Erro, email incorreto!", excecao.getMessage());
    }

    // ========================================================
    // TESTES DE AUTORIZAÇÃO E HIERARQUIA (AC3 e AC4)
    // ========================================================

    @Test
    public void devePermitirProfessorAcederAoNichoDeEstudante() {
        Authenticate auth = new Authenticate();
        Teacher professorDummy = new Teacher();

        // Professor acede a STUDENT (Nível 1 >= Nível 0) -> Deve passar livremente
        assertDoesNotThrow(() -> {
            auth.checkAuthorize(professorDummy, Authenticate.Role.STUDENT);
        });
    }

    @Test
    public void deveBarrarProfessorSeTentarAcederAoNichoDeAdmin() {
        Authenticate auth = new Authenticate();
        Teacher professorDummy = new Teacher();

        // Professor acede a ADMIN (Nível 1 < Nível 2) -> Deve lançar AuthException
        AuthException excecao = assertThrows(AuthException.class, () -> {
            auth.checkAuthorize(professorDummy, Authenticate.Role.ADMIN);
        });
    }

}
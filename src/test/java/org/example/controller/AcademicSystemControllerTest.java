package org.example.controller;

import org.example.menu.Menu;
import org.example.security.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.mockito.Mockito.*;

class AcademicSystemControllerTest {

    @Test
    @DisplayName("Deve delegar a exibição do menu para o objeto User correto")
    void deveDelegarParaOComportamentoDoMenuDoUsuario() {

        // ARRANGE (Configuração do cenário)
        AcademicSystemController controller = new AcademicSystemController();
        // Dublês de usuário e menu
        User mockUser = mock(User.class);
        Menu mockMenu = mock(Menu.class);

        // Define que quando pedir o menu do usuário, retorna o nosso dublê
        when(mockUser.getMenu()).thenReturn(mockMenu);

        //Execução da ação
        controller.direcionarParaMenu(mockUser);

        //(Verificação do resultado)
        // Garante que o controlador chamou o menu exatamente 1 vez
        verify(mockMenu, times(1)).carregarMenu(any(Scanner.class));
    }
}

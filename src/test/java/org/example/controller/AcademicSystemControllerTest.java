package org.example.controller;

import javafx.stage.Stage;
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
        Stage mockStage = mock(Stage.class);
        AcademicSystemController controller = new AcademicSystemController(mockStage);

        // Dublês (Mocks) do Usuário e do Menu Gráfico
        User mockUser = mock(User.class);
        org.example.app.MenuApp mockMenuApp = mock(org.example.app.MenuApp.class);

        // Configura o mock do usuário para retornar o mock do MenuApp quando getMenuApp() for chamado
        when(mockUser.getMenuApp()).thenReturn(mockMenuApp);

        // ACT (Execução da ação)
        controller.direcionarParaMenu(mockUser);

        // ASSERT (Verificação do resultado)
        // 1. Garante que o controlador pediu o menu correto para o usuário
        verify(mockUser, times(1)).getMenuApp();

        // 2. Garante que o controlador chamou o 'carregarMenu' passando o stage e o próprio controlador
        verify(mockMenuApp, times(1)).carregarMenu(eq(mockStage), eq(controller));
    }
}

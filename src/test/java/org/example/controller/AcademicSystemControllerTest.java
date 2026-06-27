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

        Stage mockStage = mock(Stage.class);

        // ARRANGE (Configuração do cenário)
        AcademicSystemController controller = new AcademicSystemController(mockStage);

        // Dublê de usuário
        User mockUser = mock(User.class);


        //Execução da ação
        controller.direcionarParaMenu(mockUser);

        //(Verificação do resultado)
        // Garante que o controlador chamou o menu exatamente 1 vez
        verify(mockUser, times(1)).getUsername();
    }
}

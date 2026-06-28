package org.example.app;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.controller.AcademicSystemController;
import org.example.security.User;

public class StudentMenuApp implements MenuApp {

    private final User currentUser;

    // Construtor que recebe o usuário (opcional, mas bom para seguir o padrão do seu getMenuApp)
    public StudentMenuApp(User user) {
        this.currentUser = user;
    }

    @Override
    public void carregarMenu(Stage stage,AcademicSystemController controller) {

        // --- 1. TÍTULO DO MENU ---
        Label lblTitulo = new Label("ACADEMIC SYSTEM - STUDENT MENU");
        lblTitulo.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;" + " -fx-font-family: 'Segoe UI', Arial;");

        lblTitulo.setMaxWidth(Double.MAX_VALUE);
        lblTitulo.setAlignment(Pos.CENTER);

        // --- 2. CRIANDO OS BOTÕES DE OPÇÃO ---
        Button btnViewGrades = new Button("1. View My Grades");
        Button btnLogout = new Button("0. Logout");

        // Estilizando as opções comuns (largura máxima para preencher o VBox)
        btnViewGrades.setMaxWidth(Double.MAX_VALUE);
        btnViewGrades.setStyle("-fx-alignment: BASELINE_LEFT; -fx-padding: 8 15 8 15;");

        // Estilizando o botão de Logout de forma independente (menor)
        btnLogout.setStyle("-fx-text-fill: red; -fx-font-weight: bold; -fx-padding: 8 15 8 15;");

        // --- 3. CONFIGURANDO AS AÇÕES DOS BOTÕES ---
        btnViewGrades.setOnAction(e -> {
            System.out.println("\n[Em breve] Visualização das suas notas gráfica.");
        });

        // --- 4. ALINHAMENTO DO LOGOUT FULL DIREITA ---
        HBox containerLogout = new HBox();
        containerLogout.setAlignment(Pos.CENTER_RIGHT); // Joga o botão para a extremidade direita
        containerLogout.getChildren().add(btnLogout);

        // --- 5. ORGANIZANDO O LAYOUT PRINCIPAL (VBox) ---
        VBox layout = new VBox(15); // Espaçamento vertical entre os elementos
        layout.setPadding(new Insets(25, 40, 25, 40)); // Margens internas da janela
        layout.setStyle("-fx-background-color: #f4f4f4;");

        // Adiciona os componentes na ordem correta
        layout.getChildren().addAll(
                lblTitulo,
                btnViewGrades,
                containerLogout // Adiciona o container do logout alinhado à direita
        );

        // --- CONFIGURANDO A AÇÃO DE LOGOUT ---
        btnLogout.setOnAction(e -> {
            System.out.println("Fazendo logout e retornando para a tela de login...");
            controller.mostrarTelaLogin();
        });

        // --- 6. RENDERIZANDO A CENA ---
        Scene cenaMenu = new Scene(layout, 450, 250); // Dimensão ideal para o menu compacto do estudante
        stage.setScene(cenaMenu);
        stage.setTitle("Academic System - Student Panel");
    }
}
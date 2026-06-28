package org.example.app;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.controller.AcademicSystemController;

public class LoginMenuApp {

    private final Stage stage;
    private final AcademicSystemController controller;

    public LoginMenuApp(Stage stage, AcademicSystemController controller) {
        this.stage = stage;
        this.controller = controller;
    }

    public void exibir() {

        // --- ELEMENTOS VISUAIS ---

        Label lblTitulo = new Label("ACADEMIC SYSTEM - LOGIN");
        lblTitulo.setStyle("-fx-font-weight: bold; -fx-font-size: 16px; -fx-font-family: 'Segoe UI', Arial;");
        lblTitulo.setMaxWidth(Double.MAX_VALUE);
        lblTitulo.setAlignment(javafx.geometry.Pos.CENTER);

        Label lblEmail = new Label("Email:");
        TextField txtEmail = new TextField();
        txtEmail.setPromptText("Digite seu email");

        Label lblSenha = new Label("Senha:");
        PasswordField txtSenha = new PasswordField();
        txtSenha.setPromptText("Digite sua senha");

        Button btnEntrar = new Button("Entrar");

        Label lblMensagem = new Label();

        // O CLIQUE DO BOTÃO (Substitui o Scanner e o return)
        btnEntrar.setOnAction(e -> {
            String email = txtEmail.getText();
            String senha = txtSenha.getText();

            try {
                //Passando os dados pro controlador
                controller.processarLogin(email, senha);

                lblMensagem.setText("Login efetuado com sucesso!");
                lblMensagem.setStyle("-fx-text-fill: green;");
            }catch (Exception ex){
                lblMensagem.setText("Email ou senha incorretos.");
                lblMensagem.setStyle("-fx-text-fill: red;");
            }
        });

        // Organização do Layout
        VBox layout = new VBox(12);
        layout.setPadding(new Insets(30, 25, 30, 25));
        layout.setStyle("-fx-padding: 40;-fx-background-color: #f4f4f4;");

        layout.getChildren().addAll(
                lblTitulo,
                lblEmail, txtEmail,
                lblSenha, txtSenha,
                btnEntrar,
                lblMensagem
        );

        Scene cenaLogin = new Scene(layout, 380, 320);
        stage.setScene(cenaLogin);
        stage.setTitle("Academic System - Autenticação");
    }
}

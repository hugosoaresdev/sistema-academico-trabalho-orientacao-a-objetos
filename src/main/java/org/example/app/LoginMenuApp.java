package org.example.app;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.controller.AcademicSystemController;

public class LoginMenuApp {

    private final Stage stage;
    private final AcademicSystemController controller;

    public LoginMenuApp(Stage stage,
                        AcademicSystemController controller) {

        this.stage = stage;
        this.controller = controller;

    }

    public void exibir() {

        //-----------------------------------------
        // LOGO
        //-----------------------------------------

        Image logoImagem = new Image(
                getClass().getResourceAsStream("/images/logo.png")
        );

        ImageView logo = new ImageView(logoImagem);

        logo.setFitWidth(130);
        logo.setPreserveRatio(true);

        //-----------------------------------------
        // TÍTULOS
        //-----------------------------------------

        Label lblTitulo =
                new Label("Sistema Acadêmico");

        lblTitulo.getStyleClass().add("title");

        Label lblSubtitulo =
                new Label("Gerenciamento Acadêmico");

        lblSubtitulo.getStyleClass().add("subtitle");

        //-----------------------------------------
        // CAMPOS
        //-----------------------------------------

        Label lblEmail =
                new Label("Email");

        TextField txtEmail =
                new TextField();

        txtEmail.setPromptText(
                "Digite seu email"
        );

        txtEmail.setMaxWidth(Double.MAX_VALUE);

        Label lblSenha =
                new Label("Senha");

        PasswordField txtSenha =
                new PasswordField();

        txtSenha.setPromptText(
                "Digite sua senha"
        );

        txtSenha.setMaxWidth(Double.MAX_VALUE);

        //-----------------------------------------
        // BOTÃO
        //-----------------------------------------

        Button btnEntrar =
                new Button("Entrar");

        btnEntrar.setMaxWidth(Double.MAX_VALUE);

        //-----------------------------------------
        // MENSAGEM
        //-----------------------------------------

        Label lblMensagem =
                new Label();

        //-----------------------------------------
        // ENTER
        //-----------------------------------------

        txtEmail.setOnAction(e ->
                txtSenha.requestFocus());

        txtSenha.setOnAction(e ->
                btnEntrar.fire());

        //-----------------------------------------
        // LOGIN
        //-----------------------------------------

        btnEntrar.setOnAction(e -> {

            String email =
                    txtEmail.getText().trim();

            String senha =
                    txtSenha.getText();

            if (email.isBlank() || senha.isBlank()) {

                atualizarMensagem(
                        lblMensagem,
                        "Preencha todos os campos.",
                        false
                );

                return;

            }

            try {

                controller.processarLogin(
                        email,
                        senha
                );

                atualizarMensagem(
                        lblMensagem,
                        "Login realizado com sucesso.",
                        true
                );

            } catch (Exception ex) {

                atualizarMensagem(
                        lblMensagem,
                        "Email ou senha incorretos.",
                        false
                );

            }

        });

        //-----------------------------------------
        // CARD
        //-----------------------------------------

        VBox card = new VBox(15);

        card.setAlignment(Pos.CENTER);

        card.setMaxWidth(380);

        card.getStyleClass().add("card");

        card.getChildren().addAll(

                logo,

                lblTitulo,

                lblSubtitulo,

                lblEmail,

                txtEmail,

                lblSenha,

                txtSenha,

                btnEntrar,

                lblMensagem

        );

        //-----------------------------------------
        // ROOT
        //-----------------------------------------

        StackPane root =
                new StackPane(card);

        root.setPadding(
                new Insets(30)
        );

        Scene scene =
                new Scene(root,500,600);

        scene.getStylesheets().add(

                getClass()

                        .getResource("/css/style.css")

                        .toExternalForm()

        );

        //-----------------------------------------
        // CONFIGURAÇÃO DA JANELA
        //-----------------------------------------

        stage.setTitle("Sistema Acadêmico");

        // Logo na barra da janela
        stage.getIcons().add(logoImagem);

        stage.setScene(scene);

        // Cursor começa no campo de email
        txtEmail.requestFocus();
    }

    /**
     * Atualiza a mensagem exibida ao usuário,
     * aplicando automaticamente o estilo correto.
     */
    private void atualizarMensagem(
            Label label,
            String mensagem,
            boolean sucesso) {

        label.setText(mensagem);

        label.getStyleClass().removeAll(
                "success-message",
                "error-message"
        );

        if (sucesso) {
            label.getStyleClass().add("success-message");
        } else {
            label.getStyleClass().add("error-message");
        }
    }

}
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

        // ---------- LOGO ----------

        Image logo = new Image(
                getClass().getResourceAsStream("/images/logo.png")
        );

        ImageView logoView = new ImageView(logo);
        logoView.setFitWidth(90);
        logoView.setPreserveRatio(true);

        // ---------- TÍTULO ----------

        Label titulo = new Label("Sistema Acadêmico");
        titulo.getStyleClass().add("titulo");

        Label subtitulo = new Label("Faça login para continuar");
        subtitulo.getStyleClass().add("subtitulo");

        // ---------- CAMPOS ----------

        Label lblEmail = new Label("E-mail");
        TextField txtEmail = new TextField();
        txtEmail.setPromptText("Informe seu e-mail");

        Label lblSenha = new Label("Senha");
        PasswordField txtSenha = new PasswordField();
        txtSenha.setPromptText("Informe sua senha");

        // ---------- BOTÃO ----------

        Button btnEntrar = new Button("Entrar");
        btnEntrar.setMaxWidth(Double.MAX_VALUE);

        // ---------- MENSAGEM ----------

        Label lblMensagem = new Label();

        // ---------- EVENTO ----------

        btnEntrar.setOnAction(event -> {

            try {

                controller.processarLogin(
                        txtEmail.getText(),
                        txtSenha.getText()
                );

                lblMensagem.setText("Login realizado com sucesso.");
                lblMensagem.setStyle("-fx-text-fill: #2E7D32;");

            } catch (Exception ex) {

                lblMensagem.setText("E-mail ou senha inválidos.");
                lblMensagem.setStyle("-fx-text-fill: #C62828;");
            }
        });

        txtSenha.setOnAction(event -> btnEntrar.fire());

        // ---------- LAYOUT ----------

        VBox layout = new VBox(12);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(35));

        layout.getStyleClass().add("root");

        layout.getChildren().addAll(
                logoView,
                titulo,
                subtitulo,
                lblEmail,
                txtEmail,
                lblSenha,
                txtSenha,
                btnEntrar,
                lblMensagem
        );

        Scene scene = new Scene(layout, 420, 500);

        scene.getStylesheets().add(
                getClass().getResource("/css/style.css").toExternalForm()
        );

        stage.setTitle("Sistema Acadêmico");
        stage.getIcons().add(
                new Image(getClass().getResourceAsStream("/images/logo.png"))
        );
        stage.setScene(scene);

        txtEmail.requestFocus();
    }
}
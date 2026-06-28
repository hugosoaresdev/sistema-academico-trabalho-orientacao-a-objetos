package org.example.app;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.controller.AcademicSystemController;
import org.example.security.User;

public class StudentMenuApp implements MenuApp {

    private final User currentUser;

    public StudentMenuApp(User user) {
        this.currentUser = user;
    }

    @Override
    public void carregarMenu(Stage stage, AcademicSystemController controller) {

        // ---------- TÍTULO ----------

        Label titulo = new Label("SISTEMA ACADÊMICO");
        titulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        titulo.setAlignment(Pos.CENTER);
        titulo.setMaxWidth(Double.MAX_VALUE);

        Label subtitulo = new Label("Painel do Aluno");
        subtitulo.setAlignment(Pos.CENTER);
        subtitulo.setMaxWidth(Double.MAX_VALUE);

        // ---------- BOTÕES ----------

        Button btnVisualizarNotas = new Button("Visualizar Minhas Notas");
        Button btnSair = new Button("Sair");

        configurarBotao(btnVisualizarNotas);

        btnSair.setStyle(
                "-fx-text-fill: red;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 8;"
        );

        // ---------- EVENTOS ----------

        btnVisualizarNotas.setOnAction(e -> {
            System.out.println("[Em breve] Visualização gráfica das notas do aluno.");
        });

        btnSair.setOnAction(e ->
                controller.mostrarTelaLogin());

        // ---------- LAYOUT ----------

        VBox layout = new VBox(10);

        layout.setPadding(new Insets(30));
        layout.setStyle("-fx-background-color: #f4f4f4;");

        layout.getChildren().addAll(
                titulo,
                subtitulo,
                btnVisualizarNotas,
                btnSair
        );

        Scene scene = new Scene(layout, 500, 260);

        stage.setScene(scene);
        stage.setTitle("Sistema Acadêmico - Aluno");
    }

    // ==========================================================
    // MÉTODOS AUXILIARES
    // ==========================================================

    private void configurarBotao(Button botao) {
        botao.setMaxWidth(Double.MAX_VALUE);
        botao.setStyle(
                "-fx-alignment: BASELINE_LEFT;" +
                        "-fx-padding: 8 15 8 15;"
        );
    }
}
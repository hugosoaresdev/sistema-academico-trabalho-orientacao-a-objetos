package org.example.app;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.controller.AcademicSystemController;
import org.example.domain.Assessment;
import org.example.domain.Classroom;
import org.example.security.User;

import java.util.List;

public class ClassViewApp {

    private final Stage stage;
    private final AcademicSystemController controller;
    private final User currentUser;

    public ClassViewApp(Stage stage, AcademicSystemController controller, User currentUser) {
        this.stage = stage;
        this.controller = controller;
        this.currentUser = currentUser;
    }

    public void exibir() {

        Label lblTitulo = new Label("TURMAS E AVALIAÇÕES");
        lblTitulo.setStyle("-fx-font-weight: bold; -fx-font-size: 16px; -fx-font-family: 'Segoe UI', Arial;");
        lblTitulo.setMaxWidth(Double.MAX_VALUE);
        lblTitulo.setAlignment(Pos.CENTER);

        // AC5: os dados vêm do controller, não direto do AcademicSystem
        List<Classroom> turmas = controller.listarTurmas();

        // Raiz invisível só para agrupar as turmas
        TreeItem<String> raiz = new TreeItem<>("Turmas");
        raiz.setExpanded(true);

        for (Classroom turma : turmas) {
            // AC1 + AC3: cada turma aparece com código e título
            TreeItem<String> noTurma = new TreeItem<>(
                    turma.getClassroomID() + " - " + turma.getClassroomName());
            noTurma.setExpanded(true);

            List<Assessment> avaliacoes = turma.getClassroomListOfAssessments();

            if (avaliacoes.isEmpty()) {
                // turma sem avaliações ainda aparece
                noTurma.getChildren().add(new TreeItem<>("(sem avaliações)"));
            } else {
                // AC2 + AC4: tipo, valor e peso de cada avaliação
                for (Assessment a : avaliacoes) {
                    noTurma.getChildren().add(new TreeItem<>(
                            a.getType() + "  |  valor: " + a.getValue()
                                    + "  |  peso: " + a.getWeight()));
                }
            }
            raiz.getChildren().add(noTurma);
        }

        TreeView<String> arvore = new TreeView<>(raiz);
        arvore.setShowRoot(false); // esconde o nó "Turmas", mostra só as turmas
        arvore.setPrefHeight(360);

        Label lblVazio = new Label();
        if (turmas.isEmpty()) {
            lblVazio.setText("Nenhuma turma cadastrada ainda.");
            lblVazio.setStyle("-fx-text-fill: #555;");
        }

        Button btnVoltar = new Button("Voltar");
        btnVoltar.setOnAction(e -> voltarAoMenu(stage, controller));
        HBox botoes = new HBox(10, btnVoltar);
        botoes.setAlignment(Pos.CENTER_LEFT);

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(25, 40, 25, 40));
        layout.setStyle("-fx-background-color: #f4f4f4;");
        layout.getChildren().addAll(lblTitulo, lblVazio, arvore, botoes);

        Scene cena = new Scene(layout, 480, 480);
        stage.setScene(cena);
        stage.setTitle("Academic System - Turmas e Avaliações");
    }

    // Volta para o menu correto conforme a função do usuário (admin ou professor)
    private void voltarAoMenu(Stage stage, AcademicSystemController controller) {
        currentUser.getMenuApp().carregarMenu(stage, controller);
    }
}
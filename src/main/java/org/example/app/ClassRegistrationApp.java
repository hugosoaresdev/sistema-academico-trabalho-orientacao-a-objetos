package org.example.app;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.controller.AcademicSystemController;
import org.example.domain.Classroom;
import org.example.domain.Teacher;
import org.example.exception.AcademicSystemException;
import org.example.security.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClassRegistrationApp {

    private static final Logger logger = LoggerFactory.getLogger(ClassRegistrationApp.class);

    private final Stage stage;
    private final AcademicSystemController controller;
    private final User currentUser;

    public ClassRegistrationApp(Stage stage, AcademicSystemController controller, User currentUser) {
        this.stage = stage;
        this.controller = controller;
        this.currentUser = currentUser;
    }

    public void exibir() {

        // --- TÍTULO ---
        Label lblTitulo = new Label("REGISTRAR TURMA");
        lblTitulo.setStyle("-fx-font-weight: bold; -fx-font-size: 16px; -fx-font-family: 'Segoe UI', Arial;");
        lblTitulo.setMaxWidth(Double.MAX_VALUE);
        lblTitulo.setAlignment(Pos.CENTER);

        // --- CAMPOS DA TURMA (AC1) ---
        Label lblCodigo = new Label("Código da turma (numérico):");
        TextField txtCodigo = new TextField();
        txtCodigo.setPromptText("Ex: 101");

        Label lblNomeTurma = new Label("Título da turma:");
        TextField txtNomeTurma = new TextField();
        txtNomeTurma.setPromptText("Ex: Programação Orientada a Objetos");

        // --- CAMPOS DO PROFESSOR (Classroom exige um Teacher válido) ---
        Label lblProfNome = new Label("Nome do professor:");
        TextField txtProfNome = new TextField();

        Label lblProfDisc = new Label("Disciplina do professor:");
        TextField txtProfDisc = new TextField();

        Label lblProfMat = new Label("Matrícula do professor:");
        TextField txtProfMat = new TextField();

        // --- FEEDBACK (AC2 sucesso / AC3 erro) ---
        Label lblMensagem = new Label();

        Button btnSalvar = new Button("Salvar");
        Button btnVoltar = new Button("Voltar");

        btnSalvar.setOnAction(e -> {
            try {
                // 1) Código -> Long (pode lançar NumberFormatException)
                Long classroomID = Long.parseLong(txtCodigo.getText().trim());

                // 2) Monta o professor com os dados do formulário
                Teacher teacher = new Teacher(
                        txtProfNome.getText(),
                        txtProfDisc.getText(),
                        txtProfMat.getText()
                );

                // 3) Monta a turma
                Classroom classroom = new Classroom();
                classroom.setClassroomID(classroomID);
                classroom.setClassroomName(txtNomeTurma.getText());
                classroom.setClassroomTeacher(teacher);

                // 4) DELEGA ao controller (AC4). A GUI não valida nem
                //    registra por conta própria (AC5).
                controller.registrarTurma(classroom);

                logger.info("Turma registrada via JavaFX | codigo={} | funcao={}",
                        classroomID, currentUser.getRole());

                lblMensagem.setText("Turma registrada com sucesso: " + classroomID);
                lblMensagem.setStyle("-fx-text-fill: green;");

                // limpa os campos para facilitar um novo cadastro
                txtCodigo.clear();
                txtNomeTurma.clear();
                txtProfNome.clear();
                txtProfDisc.clear();
                txtProfMat.clear();

            } catch (NumberFormatException ex) {
                lblMensagem.setText("Erro: o código da turma deve ser um número inteiro.");
                lblMensagem.setStyle("-fx-text-fill: red;");
            } catch (AcademicSystemException ex) {
                // Campo em branco (validação) ou turma duplicada (AC3)
                lblMensagem.setText(ex.getMessage());
                lblMensagem.setStyle("-fx-text-fill: red;");
            }
        });

        // Voltar: recarrega o menu do administrador
        btnVoltar.setOnAction(e ->
                new AdministratorMenuApp(currentUser).carregarMenu(stage, controller));

        btnSalvar.setMaxWidth(Double.MAX_VALUE);
        HBox botoes = new HBox(10, btnSalvar, btnVoltar);
        botoes.setAlignment(Pos.CENTER_LEFT);

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(25, 40, 25, 40));
        layout.setStyle("-fx-background-color: #f4f4f4;");
        layout.getChildren().addAll(
                lblTitulo,
                lblCodigo, txtCodigo,
                lblNomeTurma, txtNomeTurma,
                lblProfNome, txtProfNome,
                lblProfDisc, txtProfDisc,
                lblProfMat, txtProfMat,
                botoes,
                lblMensagem
        );

        Scene cena = new Scene(layout, 420, 520);
        stage.setScene(cena);
        stage.setTitle("Academic System - Registrar Turma");
    }
}
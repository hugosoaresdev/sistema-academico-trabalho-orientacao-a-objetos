package org.example.app;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.example.controller.AcademicSystemController;
import org.example.domain.Classroom;
import org.example.exception.AcademicSystemException;
import org.example.security.User;
import org.example.sistema.AcademicSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AssessmentRegistrationApp {

    private static final Logger logger = LoggerFactory.getLogger(AssessmentRegistrationApp.class);

    private final Stage stage;
    private final AcademicSystemController controller;
    private final User currentUser;

    public AssessmentRegistrationApp(Stage stage, AcademicSystemController controller, User currentUser) {
        this.stage = stage;
        this.controller = controller;
        this.currentUser = currentUser;
    }

    public void exibir() {

        Label lblTitulo = new Label("REGISTRAR AVALIAÇÃO");
        lblTitulo.setStyle("-fx-font-weight: bold; -fx-font-size: 16px; -fx-font-family: 'Segoe UI', Arial;");
        lblTitulo.setMaxWidth(Double.MAX_VALUE);
        lblTitulo.setAlignment(Pos.CENTER);

        // --- AC1: seleção de uma turma já existente ---
        Label lblTurma = new Label("Turma:");
        ComboBox<Classroom> cmbTurma = new ComboBox<>();
        cmbTurma.setItems(FXCollections.observableArrayList(
                AcademicSystem.getInstance().getClassrooms()));
        cmbTurma.setMaxWidth(Double.MAX_VALUE);
        // mostra "código - título" em vez do toString padrão do objeto
        cmbTurma.setConverter(new StringConverter<>() {
            @Override public String toString(Classroom c) {
                return (c == null) ? "" : c.getClassroomID() + " - " + c.getClassroomName();
            }
            @Override public Classroom fromString(String s) { return null; }
        });

        // --- AC2: seleção do tipo (ordem casa com 1..4 do AssessmentService) ---
        Label lblTipo = new Label("Tipo de avaliação:");
        ComboBox<String> cmbTipo = new ComboBox<>();
        cmbTipo.setItems(FXCollections.observableArrayList(
                "Exame", "Tarefa Prática", "Seminário", "Atribuição"));
        cmbTipo.setMaxWidth(Double.MAX_VALUE);

        // --- AC3: valor e peso ---
        Label lblValor = new Label("Valor:");
        TextField txtValor = new TextField();
        txtValor.setPromptText("Ex: 10.0");

        Label lblPeso = new Label("Peso:");
        TextField txtPeso = new TextField();
        txtPeso.setPromptText("Ex: 0.4");

        Label lblMensagem = new Label();

        Button btnSalvar = new Button("Salvar");
        Button btnVoltar = new Button("Voltar");

        btnSalvar.setOnAction(e -> {
            try {
                Classroom turma = cmbTurma.getValue();
                if (turma == null) {
                    lblMensagem.setText("Selecione uma turma.");
                    lblMensagem.setStyle("-fx-text-fill: red;");
                    return;
                }

                // índice 0..3 + 1 => tipo 1..4 que o service espera
                int tipo = cmbTipo.getSelectionModel().getSelectedIndex() + 1;
                if (tipo == 0) { // nada selecionado (índice era -1)
                    lblMensagem.setText("Selecione o tipo de avaliação.");
                    lblMensagem.setStyle("-fx-text-fill: red;");
                    return;
                }

                double value = Double.parseDouble(txtValor.getText().trim().replace(",", "."));
                double weight = Double.parseDouble(txtPeso.getText().trim().replace(",", "."));

                // AC6: delega ao controller (que delega ao AssessmentService)
                controller.registrarAvaliacao(turma.getClassroomID(), tipo, value, weight);

                logger.info("Avaliação registrada via JavaFX | turma={} | tipo={} | funcao={}",
                        turma.getClassroomID(), tipo, currentUser.getRole());

                lblMensagem.setText("Avaliação registrada na turma " + turma.getClassroomID() + ".");
                lblMensagem.setStyle("-fx-text-fill: green;");

                txtValor.clear();
                txtPeso.clear();

            } catch (NumberFormatException ex) {
                lblMensagem.setText("Erro: valor e peso devem ser números (ex: 10.0).");
                lblMensagem.setStyle("-fx-text-fill: red;");
            } catch (AcademicSystemException ex) {
                lblMensagem.setText(ex.getMessage());
                lblMensagem.setStyle("-fx-text-fill: red;");
            }
        });

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
                lblTurma, cmbTurma,
                lblTipo, cmbTipo,
                lblValor, txtValor,
                lblPeso, txtPeso,
                botoes,
                lblMensagem
        );

        Scene cena = new Scene(layout, 420, 460);
        stage.setScene(cena);
        stage.setTitle("Academic System - Registrar Avaliação");
    }
}
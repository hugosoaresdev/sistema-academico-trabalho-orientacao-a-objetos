package org.example.app;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.controller.AcademicSystemController;
import org.example.repository.PersistenceType;
import org.example.security.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PersistenceConfigApp {

    private static final Logger logger = LoggerFactory.getLogger(PersistenceConfigApp.class);

    private final Stage stage;
    private final AcademicSystemController controller;
    private final User currentUser;

    public PersistenceConfigApp(Stage stage, AcademicSystemController controller, User currentUser) {
        this.stage = stage;
        this.controller = controller;
        this.currentUser = currentUser;
    }

    public void exibir() {

        Label lblTitulo = new Label("CONFIGURAR PERSISTÊNCIA");
        lblTitulo.setStyle("-fx-font-weight: bold; -fx-font-size: 16px; -fx-font-family: 'Segoe UI', Arial;");
        lblTitulo.setMaxWidth(Double.MAX_VALUE);
        lblTitulo.setAlignment(Pos.CENTER);

        Label lblAtual = new Label("Tipo atual: " + controller.getPersistenceType());

        // AC1/AC2/AC3: as três opções (TXT, XML, JSON) vêm direto do enum
        Label lblTipo = new Label("Selecione o tipo de persistência:");
        ComboBox<PersistenceType> cmbTipo = new ComboBox<>();
        cmbTipo.setItems(FXCollections.observableArrayList(PersistenceType.values()));
        cmbTipo.setValue(controller.getPersistenceType()); // já abre com o atual selecionado
        cmbTipo.setMaxWidth(Double.MAX_VALUE);

        Label lblMensagem = new Label();

        Button btnConfirmar = new Button("Confirmar");
        Button btnVoltar = new Button("Voltar");

        btnConfirmar.setOnAction(e -> {
            PersistenceType escolhido = cmbTipo.getValue();
            if (escolhido == null) {
                lblMensagem.setText("Selecione um tipo de persistência.");
                lblMensagem.setStyle("-fx-text-fill: red;");
                return;
            }

            // AC4 + AC5: delega a atualização ao controller
            controller.configurarPersistencia(escolhido);

            // TUS-2372 (AC7): registra a mudança de configuração para auditoria
            logger.info("Tipo de persistência alterado para {} | função={}",
                    escolhido, currentUser.getRole());

            lblAtual.setText("Tipo atual: " + escolhido);
            lblMensagem.setText("Persistência configurada para: " + escolhido);
            lblMensagem.setStyle("-fx-text-fill: green;");
        });

        btnVoltar.setOnAction(e ->
                new AdministratorMenuApp(currentUser).carregarMenu(stage, controller));

        btnConfirmar.setMaxWidth(Double.MAX_VALUE);
        HBox botoes = new HBox(10, btnConfirmar, btnVoltar);
        botoes.setAlignment(Pos.CENTER_LEFT);

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(25, 40, 25, 40));
        layout.setStyle("-fx-background-color: #f4f4f4;");
        layout.getChildren().addAll(lblTitulo, lblAtual, lblTipo, cmbTipo, botoes, lblMensagem);

        Scene cena = new Scene(layout, 420, 320);
        stage.setScene(cena);
        stage.setTitle("Academic System - Configurar Persistência");
    }
}
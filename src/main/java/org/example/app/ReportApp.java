package org.example.app;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.controller.AcademicSystemController;
import org.example.security.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReportApp {

    private static final Logger logger = LoggerFactory.getLogger(ReportApp.class);

    private final Stage stage;
    private final AcademicSystemController controller;
    private final User currentUser;
    private final int relatorioInicial; // 1=resumo, 2=peso, 3=config, 0=nenhum

    public ReportApp(Stage stage, AcademicSystemController controller, User currentUser, int relatorioInicial) {
        this.stage = stage;
        this.controller = controller;
        this.currentUser = currentUser;
        this.relatorioInicial = relatorioInicial;
    }

    public void exibir() {

        Label lblTitulo = new Label("RELATÓRIOS");
        lblTitulo.setStyle("-fx-font-weight: bold; -fx-font-size: 16px; -fx-font-family: 'Segoe UI', Arial;");
        lblTitulo.setMaxWidth(Double.MAX_VALUE);
        lblTitulo.setAlignment(Pos.CENTER);

        // AC5: o relatório é exibido dentro do app, nesta área de texto
        TextArea txtSaida = new TextArea();
        txtSaida.setEditable(false);
        txtSaida.setPrefRowCount(16);
        txtSaida.setStyle("-fx-font-family: 'Consolas', monospace;");

        Button btnResumo = new Button("Relatório Resumido de Avaliações");
        Button btnPeso = new Button("Relatório de Peso das Avaliações");
        Button btnConfig = new Button("Relatório de Configuração de Persistência");
        Button btnVoltar = new Button("Voltar");

        btnResumo.setMaxWidth(Double.MAX_VALUE);
        btnPeso.setMaxWidth(Double.MAX_VALUE);
        btnConfig.setMaxWidth(Double.MAX_VALUE);

        btnResumo.setOnAction(e -> mostrarRelatorio(txtSaida, 1));
        btnPeso.setOnAction(e -> mostrarRelatorio(txtSaida, 2));
        btnConfig.setOnAction(e -> mostrarRelatorio(txtSaida, 3));
        btnVoltar.setOnAction(e -> currentUser.getMenuApp().carregarMenu(stage, controller));

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(25, 40, 25, 40));
        layout.setStyle("-fx-background-color: #f4f4f4;");
        layout.getChildren().addAll(lblTitulo, btnResumo, btnPeso);

        // AC3 + AC4: só o ADMIN vê o relatório de configuração de persistência
        if (currentUser.isAdmin()) {
            layout.getChildren().add(btnConfig);
        }

        HBox botoes = new HBox(10, btnVoltar);
        botoes.setAlignment(Pos.CENTER_LEFT);
        layout.getChildren().addAll(txtSaida, botoes);

        // Se a tela foi aberta por um botão específico do menu, já mostra aquele relatório
        if (relatorioInicial >= 1 && relatorioInicial <= 3
                && !(relatorioInicial == 3 && !currentUser.isAdmin())) { // professor nunca gera o de persistência
            mostrarRelatorio(txtSaida, relatorioInicial);
        }

        Scene cena = new Scene(layout, 520, 520);
        stage.setScene(cena);
        stage.setTitle("Academic System - Relatórios");
    }

    // AC6: delega a geração ao controller.
    // TUS-2394: registra a geração com a função do usuário (auditoria).
    private void mostrarRelatorio(TextArea area, int qual) {
        String texto;
        switch (qual) {
            case 1 -> {
                logger.info("Relatório gerado: resumo de avaliações | função={}", currentUser.getRole());
                texto = controller.gerarRelatorioResumo();
            }
            case 2 -> {
                logger.info("Relatório gerado: peso das avaliações | função={}", currentUser.getRole());
                texto = controller.gerarRelatorioPeso();
            }
            case 3 -> {
                logger.info("Relatório gerado: configuração de persistência | função={}", currentUser.getRole());
                texto = controller.gerarRelatorioPersistencia();
            }
            default -> { return; }
        }
        area.setText(texto);
    }
}
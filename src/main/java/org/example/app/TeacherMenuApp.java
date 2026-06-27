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
import org.example.service.ReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TeacherMenuApp implements MenuApp{

    private static final Logger logger = LoggerFactory.getLogger(TeacherMenuApp.class);

    private final User currentUser;
    private final ReportService reportService = new ReportService();

    public TeacherMenuApp(User user){
        this.currentUser = user;
    }

    @Override
    public void carregarMenu(Stage stage, AcademicSystemController controller) {

        // --- 1. TÍTULO DO MENU ---
        Label lblTitulo = new Label("ACADEMIC SYSTEM - TEACHER MENU");
        lblTitulo.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;" + " -fx-font-family: 'Segoe UI', Arial;");

        lblTitulo.setMaxWidth(Double.MAX_VALUE);
        lblTitulo.setAlignment(Pos.CENTER);

        // --- 2. CRIANDO OS BOTÕES DE OPÇÃO ---
        Button btnListClassrooms = new Button("1. List Classrooms");
        Button btnReportSummary = new Button("2. View Class Assessment Report");
        Button btnReportWeight = new Button("3. View Assessment Weight Report");
        Button btnLogout = new Button("0. Logout");

        // Estilizando as opções comuns (largura máxima e alinhadas à esquerda)
        for (Button btn : new Button[]{btnListClassrooms, btnReportSummary, btnReportWeight}) {
            btn.setMaxWidth(Double.MAX_VALUE);
            btn.setStyle("-fx-alignment: BASELINE_LEFT; -fx-padding: 8 15 8 15;");
        }

        // Estilizando o botão de Logout independente (menor)
        btnLogout.setStyle("-fx-text-fill: red; -fx-font-weight: bold; -fx-padding: 8 15 8 15;");

        // --- 3. CONFIGURANDO AS AÇÕES DOS BOTÕES (Ações do seu switch original) ---
        btnListClassrooms.setOnAction(e -> {
            System.out.println("\n[Em breve] Listagem de turmas gráfica.");
        });

        btnReportSummary.setOnAction(e -> {
            // TUS-2394: mantendo sua auditoria por logger intacta
            logger.info("Relatório gerado: resumo de avaliações | função={}", currentUser.getRole());
            System.out.println("\n" + reportService.generateClassAssessmentSummaryReport());
        });

        btnReportWeight.setOnAction(e -> {
            // TUS-2394: mantendo sua auditoria por logger intacta
            logger.info("Relatório gerado: peso das avaliações | função={}", currentUser.getRole());
            System.out.println("\n" + reportService.generateAssessmentWeightReport());
        });

        // --- 4. ALINHAMENTO DO LOGOUT FULL DIREITA ---
        HBox containerLogout = new HBox();
        containerLogout.setAlignment(Pos.CENTER_RIGHT); // Força o botão a ir para o extremo direito
        containerLogout.getChildren().add(btnLogout);

        // --- 5. ORGANIZANDO O LAYOUT PRINCIPAL (VBox) ---
        VBox layout = new VBox(12); // Espaçamento consistente entre as linhas
        layout.setPadding(new Insets(25, 40, 25, 40)); // Mesmas margens internas
        layout.setStyle("-fx-background-color: #f4f4f4;");

        // Adiciona todos na ordem correta
        layout.getChildren().addAll(
                lblTitulo,
                btnListClassrooms,
                btnReportSummary,
                btnReportWeight,
                containerLogout // Container que joga o logout na direita
        );

        // --- CONFIGURANDO A AÇÃO DE LOGOUT ---
        btnLogout.setOnAction(e -> {
            System.out.println("Fazendo logout e retornando para a tela de login...");
            controller.mostrarTelaLogin();
        });

        // --- 6. RENDERIZANDO A CENA ---
        Scene cenaMenu = new Scene(layout, 450, 320); // Resolução sob medida para o menu do professor
        stage.setScene(cenaMenu);
        stage.setTitle("Academic System - Teacher Panel");
    }
}

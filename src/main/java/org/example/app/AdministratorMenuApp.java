package org.example.app;

import org.example.controller.AcademicSystemController;
import org.example.security.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AdministratorMenuApp implements MenuApp {

    private final User currentUser;

    public AdministratorMenuApp(User user) {
        this.currentUser = user;
    }

    @Override
    public void carregarMenu(Stage stage, AcademicSystemController controller) {

        // --- 1. TÍTULO DO MENU ---

        Label lblTitulo = new Label("ACADEMIC SYSTEM - ADMINISTRATOR MENU");
        lblTitulo.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;" + " -fx-font-family: 'Segoe UI', Arial;");

        lblTitulo.setMaxWidth(Double.MAX_VALUE);
        lblTitulo.setAlignment(Pos.CENTER);

        // --- 2. CRIANDO OS BOTÕES DE OPÇÃO ---

        Button btnRegisterTemplate = new Button("1. Register Classroom");
        Button btnRegisterAssessment = new Button("2. Register Assessment in Classroom");
        Button btnListClassrooms = new Button("3. List Classrooms");
        Button btnSaveData = new Button("4. Save Data to File");
        Button btnConfigurePersistence = new Button("5. Configure Persistence Type");
        Button btnReportSummary = new Button("6. Class Assessment Report");
        Button btnReportWeight = new Button("7. Assessment Weight Report");
        Button btnReportConfig = new Button("8. Persistence Configuration Report");
        Button btnLogout = new Button("0. Logout");

        // Estilizando os botões para ocuparem a largura ideal e ficarem organizados
        for (Button btn : new Button[]{
                btnRegisterTemplate,
                btnRegisterAssessment,
                btnListClassrooms,
                btnSaveData,
                btnConfigurePersistence,
                btnReportSummary,
                btnReportWeight,
                btnReportConfig}
        ) {
            btn.setMaxWidth(Double.MAX_VALUE); // Faz todos os botões terem o mesmo tamanho horizontal
            btn.setStyle("-fx-alignment: BASELINE_LEFT; -fx-padding: 8 15 8 15;"); // Alinha o texto deles à esquerda internamente
        }

        // Estilo especial para o botão de Logout se destacar
        btnLogout.setStyle("-fx-alignment: CENTER; -fx-text-fill: red; -fx-font-weight: bold; -fx-padding: 8;");
        // Criamos uma caixinha horizontal (HBox) só para o Logout e mandamos ela alinhar tudo à DIREITA
        javafx.scene.layout.HBox containerLogout = new javafx.scene.layout.HBox();
        containerLogout.setAlignment(javafx.geometry.Pos.CENTER_RIGHT); // Joga o conteúdo full direita
        containerLogout.getChildren().add(btnLogout);

        // --- 3. CONFIGURANDO AS AÇÕES DOS BOTÕES (Substitui o switch/case) ---
        btnRegisterTemplate.setOnAction(e -> registrarTurma(stage, controller));
        btnRegisterAssessment.setOnAction(e -> registrarAvaliacao(stage, controller));

        btnListClassrooms.setOnAction(e ->
                new ClassViewApp(stage, controller, currentUser).exibir());

        btnSaveData.setOnAction(e -> salvarDados(controller));
        btnConfigurePersistence.setOnAction(e -> configurarTipoPersistencia(stage, controller));
        btnReportSummary.setOnAction(e -> new ReportApp(stage, controller, currentUser, 1).exibir());
        btnReportWeight.setOnAction(e -> new ReportApp(stage, controller, currentUser, 2).exibir());
        btnReportConfig.setOnAction(e -> new ReportApp(stage, controller, currentUser, 3).exibir());

        // --- 4. ORGANIZANDO O LAYOUT GRÁFICO (VBox) ---
        VBox layout = new VBox(10); // 10 pixels de espaço entre cada botão
        layout.setPadding(new Insets(25, 40, 25, 40)); // Margens da borda da janela
        layout.setStyle("-fx-background-color: #f4f4f4;");

        // Adiciona todos os componentes criados ao layout na ordem correta
        layout.getChildren().addAll(
                lblTitulo,
                btnRegisterTemplate,
                btnRegisterAssessment,
                btnListClassrooms,
                btnSaveData,
                btnConfigurePersistence,
                btnReportSummary,
                btnReportWeight,
                btnReportConfig,
                btnLogout
        );

        // --- CONFIGURANDO A AÇÃO DE LOGOUT (US-2379) ---
        btnLogout.setOnAction(e -> controller.logout(currentUser));

        // --- 5. RENDERIZANDO A CENA ---
        Scene cenaMenu = new Scene(layout, 480, 500); // Resolução confortável para caber as 9 opções
        stage.setScene(cenaMenu);
        stage.setTitle("Academic System - Administrator Panel");
    }

    // --- MÉTODOS DE SUPORTE (Agora adaptados para receber o Stage em vez de Scanner) ---

    private void registrarTurma(Stage stage, AcademicSystemController controller) {
        new ClassRegistrationApp(stage, controller, currentUser).exibir();
    }

    private void registrarAvaliacao(Stage stage, AcademicSystemController controller) {
        new AssessmentRegistrationApp(stage, controller, currentUser).exibir();
    }

    private void configurarTipoPersistencia(Stage stage, AcademicSystemController controller) {
        new PersistenceConfigApp(stage, controller, currentUser).exibir();
    }

    private void salvarDados(AcademicSystemController controller) {
        try {
            controller.salvarDados();
            new javafx.scene.control.Alert(
                    javafx.scene.control.Alert.AlertType.INFORMATION,
                    "Dados salvos com sucesso no formato " + controller.getPersistenceType() + "."
            ).showAndWait();
        } catch (java.io.IOException ex) {
            new javafx.scene.control.Alert(
                    javafx.scene.control.Alert.AlertType.ERROR,
                    "Erro ao salvar dados: " + ex.getMessage()
            ).showAndWait();
        }
    }

    private void gerarRelatorioResumoAvaliacoes() {
        System.out.println("Gerando relatório 1...");
    }

    private void gerarRelatorioPesoAvaliacoes() {
        System.out.println("Gerando relatório 2...");
    }

    private void gerarRelatorioConfiguracaoPersistencia() {
        System.out.println("Gerando relatório 3...");
    }

}

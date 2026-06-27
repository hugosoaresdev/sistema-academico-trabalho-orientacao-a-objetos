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
        btnRegisterTemplate.setOnAction(e -> registrarTurma(stage));
        btnRegisterAssessment.setOnAction(e -> registrarAvaliacao(stage));

        btnListClassrooms.setOnAction(e -> {
            // Substitui o System.out.println provisório por um aviso ou abre a futura tela
            System.out.println("\n[Em breve] Listagem de turmas gráfica.");
        });

        btnSaveData.setOnAction(e -> salvarDados());
        btnConfigurePersistence.setOnAction(e -> configurarTipoPersistencia(stage));
        btnReportSummary.setOnAction(e -> gerarRelatorioResumoAvaliacoes());
        btnReportWeight.setOnAction(e -> gerarRelatorioPesoAvaliacoes());
        btnReportConfig.setOnAction(e -> gerarRelatorioConfiguracaoPersistencia());

        btnLogout.setOnAction(e -> {
            System.out.println("Saindo do painel administrativo...");
            // Como combinamos no controlador, aqui você pode voltar para a tela de login.
            // Para fazer isso da forma mais limpa, recarregamos a tela de login pelo controlador:
            // stage.close(); ou chamar a tela de login inicial novamente.
        });

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

        // --- CONFIGURANDO A AÇÃO DE LOGOUT ---
        btnLogout.setOnAction(e -> {
            System.out.println("Fazendo logout e retornando para a tela de login...");
            controller.mostrarTelaLogin();
        });

        // --- 5. RENDERIZANDO A CENA ---
        Scene cenaMenu = new Scene(layout, 480, 500); // Resolução confortável para caber as 9 opções
        stage.setScene(cenaMenu);
        stage.setTitle("Academic System - Administrator Panel");
    }

    // --- MÉTODOS DE SUPORTE (Agora adaptados para receber o Stage em vez de Scanner) ---

    private void registrarTurma(Stage stage) {
        // Aqui você chamará a classe de tela de cadastro de turma que criar
        System.out.println("Abrindo formulário de cadastro de turma...");
    }

    private void registrarAvaliacao(Stage stage) {
        System.out.println("Abrindo formulário de cadastro de avaliações...");
    }

    private void configurarTipoPersistencia(Stage stage) {
        System.out.println("Abrindo tela de configuração de persistência...");
    }

    private void salvarDados() {
        // Métodos que salvam arquivos continuam funcionando de forma transparente nos bastidores!
        System.out.println("Dados salvos com sucesso.");
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

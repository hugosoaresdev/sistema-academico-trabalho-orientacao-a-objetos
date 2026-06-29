package org.example.app;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.controller.AcademicSystemController;
import org.example.security.User;

public class AdministratorMenuApp implements MenuApp {

    private final User currentUser;

    public AdministratorMenuApp(User user) {
        this.currentUser = user;
    }

    @Override
    public void carregarMenu(Stage stage, AcademicSystemController controller) {

        //------------------------------------
        // LOGO
        //------------------------------------

        ImageView logo = new ImageView();

        try {

            logo.setImage(
                    new Image(
                            getClass().getResourceAsStream("/images/logo.png")
                    )
            );

        } catch (Exception ignored) {
        }

        logo.setFitWidth(110);
        logo.setPreserveRatio(true);

        //------------------------------------
        // TÍTULOS
        //------------------------------------

        Label lblTitulo = new Label("Sistema Acadêmico");
        lblTitulo.getStyleClass().add("title");

        Label lblSubtitulo = new Label("Painel do Administrador");
        lblSubtitulo.getStyleClass().add("subtitle");

        //------------------------------------
        // BOTÕES
        //------------------------------------

        Button btnRegisterTemplate =
                criarBotao("📚  Registrar Turma");

        Button btnRegisterAssessment =
                criarBotao("📝  Registrar Avaliação");

        Button btnListClassrooms =
                criarBotao("📖  Listar Turmas");

        Button btnSaveData =
                criarBotao("💾  Salvar Dados");

        Button btnConfigurePersistence =
                criarBotao("⚙  Configurar Persistência");

        Button btnReportSummary =
                criarBotao("📊  Relatório Geral");

        Button btnReportWeight =
                criarBotao("📈  Relatório de Pesos");

        Button btnReportConfig =
                criarBotao("📋  Configuração da Persistência");

        Button btnLogout = new Button("Logout");

        btnLogout.setMaxWidth(Double.MAX_VALUE);
        btnLogout.getStyleClass().add("logout-button");

        //------------------------------------
        // EVENTOS
        //------------------------------------

        btnRegisterTemplate.setOnAction(e ->
                registrarTurma(stage, controller));

        btnRegisterAssessment.setOnAction(e ->
                registrarAvaliacao(stage, controller));

        btnListClassrooms.setOnAction(e ->
                new ClassViewApp(stage, controller, currentUser).exibir());

        btnSaveData.setOnAction(e ->
                salvarDados(controller));

        btnConfigurePersistence.setOnAction(e ->
                configurarTipoPersistencia(stage, controller));

        btnReportSummary.setOnAction(e ->
                new ReportApp(stage, controller, currentUser, 1).exibir());

        btnReportWeight.setOnAction(e ->
                new ReportApp(stage, controller, currentUser, 2).exibir());

        btnReportConfig.setOnAction(e ->
                new ReportApp(stage, controller, currentUser, 3).exibir());

        btnLogout.setOnAction(e ->
                controller.logout(currentUser));

        //------------------------------------
        // ESPAÇADOR
        //------------------------------------

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        //------------------------------------
        // CARD
        //------------------------------------

        VBox card = new VBox(12);

        card.setAlignment(Pos.TOP_CENTER);

        card.getStyleClass().add("card");

        card.getChildren().addAll(

                logo,

                lblTitulo,

                lblSubtitulo,

                btnRegisterTemplate,

                btnRegisterAssessment,

                btnListClassrooms,

                btnSaveData,

                btnConfigurePersistence,

                btnReportSummary,

                btnReportWeight,

                btnReportConfig,

                spacer,

                btnLogout

        );

        //------------------------------------
        // ROOT
        //------------------------------------

        StackPane root = new StackPane(card);

        root.setPadding(new Insets(30));

        Scene scene = new Scene(root, 650, 720);

        scene.getStylesheets().add(

                getClass()

                        .getResource("/css/style.css")

                        .toExternalForm()

        );

        stage.setTitle("Sistema Acadêmico");

        stage.setScene(scene);
    }

    /**
     * Cria um botão padrão do menu.
     */
    private Button criarBotao(String texto) {

        Button button = new Button(texto);

        button.setMaxWidth(Double.MAX_VALUE);

        button.getStyleClass().add("menu-button");

        return button;
    }

    /**
     * Abre a tela de cadastro de turma.
     */
    private void registrarTurma(Stage stage, AcademicSystemController controller) {

        new ClassRegistrationApp(
                stage,
                controller,
                currentUser
        ).exibir();

    }

    /**
     * Abre a tela de cadastro de avaliação.
     */
    private void registrarAvaliacao(Stage stage, AcademicSystemController controller) {

        new AssessmentRegistrationApp(
                stage,
                controller,
                currentUser
        ).exibir();

    }

    /**
     * Abre a tela de configuração da persistência.
     */
    private void configurarTipoPersistencia(
            Stage stage,
            AcademicSystemController controller) {

        new PersistenceConfigApp(
                stage,
                controller,
                currentUser
        ).exibir();

    }

    /**
     * Salva os dados da aplicação.
     */
    private void salvarDados(AcademicSystemController controller) {

        try {

            controller.salvarDados();

            javafx.scene.control.Alert alert =
                    new javafx.scene.control.Alert(
                            javafx.scene.control.Alert.AlertType.INFORMATION
                    );

            alert.setTitle("Sucesso");

            alert.setHeaderText("Operação realizada");

            alert.setContentText(
                    "Os dados foram salvos com sucesso.\n\nFormato: "
                            + controller.getPersistenceType()
            );

            alert.showAndWait();

        } catch (java.io.IOException ex) {

            javafx.scene.control.Alert alert =
                    new javafx.scene.control.Alert(
                            javafx.scene.control.Alert.AlertType.ERROR
                    );

            alert.setTitle("Erro");

            alert.setHeaderText("Falha ao salvar");

            alert.setContentText(ex.getMessage());

            alert.showAndWait();

        }

    }

    /**
     * Futuras implementações.
     */
    private void gerarRelatorioResumoAvaliacoes() {

        System.out.println("Gerando relatório geral...");

    }

    private void gerarRelatorioPesoAvaliacoes() {

        System.out.println("Gerando relatório de pesos...");

    }

    private void gerarRelatorioConfiguracaoPersistencia() {

        System.out.println("Gerando relatório de configuração...");

    }

}

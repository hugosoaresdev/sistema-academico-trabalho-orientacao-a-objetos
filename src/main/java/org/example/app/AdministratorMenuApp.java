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

        // ---------- LOGO ----------

        ImageView logo = new ImageView(
                new Image(getClass().getResourceAsStream("/images/logo.png"))
        );
        logo.setFitWidth(80);
        logo.setPreserveRatio(true);

        // ---------- TÍTULO ----------

        Label titulo = new Label("Sistema Acadêmico");
        titulo.getStyleClass().add("menu-title");

        Label perfil = new Label("Administrador");
        perfil.getStyleClass().add("subtitulo");

        Label usuario = new Label("Usuário: " + currentUser.getUsername());
        usuario.getStyleClass().add("menu-user");

        // ---------- BOTÕES ----------

        Button btnCadastrarTurma = new Button("Cadastrar turma");
        Button btnCadastrarAvaliacao = new Button("Cadastrar avaliação");
        Button btnListarTurmas = new Button("Listar turmas");
        Button btnSalvar = new Button("Salvar dados");
        Button btnPersistencia = new Button("Configurar persistência");
        Button btnRelatorioResumo = new Button("Relatório das avaliações");
        Button btnRelatorioPeso = new Button("Relatório dos pesos");
        Button btnRelatorioPersistencia = new Button("Relatório da persistência");

        Button btnSair = new Button("Sair");
        btnSair.getStyleClass().add("logout-button");

        Button[] botoes = {
                btnCadastrarTurma,
                btnCadastrarAvaliacao,
                btnListarTurmas,
                btnSalvar,
                btnPersistencia,
                btnRelatorioResumo,
                btnRelatorioPeso,
                btnRelatorioPersistencia
        };

        for (Button botao : botoes) {
            botao.setMaxWidth(Double.MAX_VALUE);
        }

        // ---------- AÇÕES ----------

        btnCadastrarTurma.setOnAction(e ->
                new ClassRegistrationApp(stage, controller, currentUser).exibir());

        btnCadastrarAvaliacao.setOnAction(e ->
                new AssessmentRegistrationApp(stage, controller, currentUser).exibir());

        btnListarTurmas.setOnAction(e ->
                new ClassViewApp(stage, controller, currentUser).exibir());

        btnPersistencia.setOnAction(e ->
                new PersistenceConfigApp(stage, controller, currentUser).exibir());

        btnRelatorioResumo.setOnAction(e ->
                new ReportApp(stage, controller, currentUser, 1).exibir());

        btnRelatorioPeso.setOnAction(e ->
                new ReportApp(stage, controller, currentUser, 2).exibir());

        btnRelatorioPersistencia.setOnAction(e ->
                new ReportApp(stage, controller, currentUser, 3).exibir());

        btnSalvar.setOnAction(e -> {
            try {

                controller.salvarDados();

                new javafx.scene.control.Alert(
                        javafx.scene.control.Alert.AlertType.INFORMATION,
                        "Dados salvos com sucesso."
                ).showAndWait();

            } catch (Exception ex) {

                new javafx.scene.control.Alert(
                        javafx.scene.control.Alert.AlertType.ERROR,
                        ex.getMessage()
                ).showAndWait();
            }
        });

        btnSair.setOnAction(e -> controller.mostrarTelaLogin());

        // ---------- LAYOUT ----------

        Region espacador = new Region();
        VBox.setVgrow(espacador, Priority.ALWAYS);

        VBox layout = new VBox(12);

        layout.setAlignment(Pos.TOP_CENTER);
        layout.setPadding(new Insets(30));

        layout.getStyleClass().add("root");

        layout.getChildren().addAll(
                logo,
                titulo,
                perfil,
                usuario,

                btnCadastrarTurma,
                btnCadastrarAvaliacao,
                btnListarTurmas,
                btnSalvar,
                btnPersistencia,
                btnRelatorioResumo,
                btnRelatorioPeso,
                btnRelatorioPersistencia,

                espacador,
                btnSair
        );

        Scene scene = new Scene(layout, 520, 640);

        scene.getStylesheets().add(
                getClass().getResource("/css/style.css").toExternalForm()
        );

        stage.setTitle("Sistema Acadêmico");
        stage.setScene(scene);
    }
}
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

public class TeacherMenuApp implements MenuApp {

    private final User currentUser;

    public TeacherMenuApp(User user) {
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

        Label perfil = new Label("Professor");
        perfil.getStyleClass().add("subtitulo");

        Label usuario = new Label("Usuário: " + currentUser.getUsername());
        usuario.getStyleClass().add("menu-user");

        // ---------- BOTÕES ----------

        Button btnListarTurmas = new Button("Listar turmas");
        Button btnRelatorioResumo = new Button("Relatório das avaliações");
        Button btnRelatorioPeso = new Button("Relatório dos pesos");

        Button btnSair = new Button("Sair");
        btnSair.getStyleClass().add("logout-button");

        Button[] botoes = {
                btnListarTurmas,
                btnRelatorioResumo,
                btnRelatorioPeso
        };

        for (Button botao : botoes) {
            botao.setMaxWidth(Double.MAX_VALUE);
        }

        // ---------- EVENTOS ----------

        btnListarTurmas.setOnAction(e ->
                new ClassViewApp(stage, controller, currentUser).exibir());

        btnRelatorioResumo.setOnAction(e ->
                new ReportApp(stage, controller, currentUser, 1).exibir());

        btnRelatorioPeso.setOnAction(e ->
                new ReportApp(stage, controller, currentUser, 2).exibir());

        btnSair.setOnAction(e ->
                controller.mostrarTelaLogin());

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

                btnListarTurmas,
                btnRelatorioResumo,
                btnRelatorioPeso,

                espacador,
                btnSair
        );

        Scene scene = new Scene(layout, 520, 500);

        scene.getStylesheets().add(
                getClass().getResource("/css/style.css").toExternalForm()
        );

        stage.setTitle("Sistema Acadêmico");
        stage.setScene(scene);
    }
}
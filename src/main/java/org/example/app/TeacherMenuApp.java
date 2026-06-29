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

public class TeacherMenuApp implements MenuApp {

    private final User currentUser;

    public TeacherMenuApp(User user) {
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
        // TÍTULO
        //------------------------------------

        Label lblTitulo = new Label("Sistema Acadêmico");
        lblTitulo.getStyleClass().add("title");

        Label lblSubtitulo = new Label("Painel do Professor");
        lblSubtitulo.getStyleClass().add("subtitle");

        //------------------------------------
        // BOTÕES
        //------------------------------------

        Button btnListClassrooms =
                criarBotao("📚  Listar Turmas");

        Button btnReportSummary =
                criarBotao("📊  Relatório de Avaliações");

        Button btnReportWeight =
                criarBotao("📈  Relatório de Pesos");

        Button btnLogout = new Button("Logout");

        btnLogout.setMaxWidth(Double.MAX_VALUE);
        btnLogout.getStyleClass().add("logout-button");

        //------------------------------------
        // EVENTOS
        //------------------------------------

        btnListClassrooms.setOnAction(e ->
                new ClassViewApp(stage, controller, currentUser).exibir());

        btnReportSummary.setOnAction(e ->
                new ReportApp(stage, controller, currentUser, 1).exibir());

        btnReportWeight.setOnAction(e ->
                new ReportApp(stage, controller, currentUser, 2).exibir());

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

                btnListClassrooms,

                btnReportSummary,

                btnReportWeight,

                spacer,

                btnLogout

        );

        //------------------------------------
        // ROOT
        //------------------------------------

        StackPane root = new StackPane(card);

        root.setPadding(new Insets(30));

        Scene scene = new Scene(root, 600, 600);

        scene.getStylesheets().add(
                getClass()
                        .getResource("/css/style.css")
                        .toExternalForm()
        );

        stage.setTitle("Sistema Acadêmico");

        stage.getIcons().add(
                new Image(
                        getClass().getResourceAsStream("/images/logo.png")
                )
        );

        stage.setScene(scene);
    }

    /**
     * Cria um botão padrão para o menu.
     */
    private Button criarBotao(String texto) {

        Button button = new Button(texto);

        button.setMaxWidth(Double.MAX_VALUE);

        button.getStyleClass().add("menu-button");

        return button;
    }
}
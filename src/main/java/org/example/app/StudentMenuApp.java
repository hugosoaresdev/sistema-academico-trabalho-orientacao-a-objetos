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

public class StudentMenuApp implements MenuApp {

    private final User currentUser;

    public StudentMenuApp(User user) {
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

        Label lblSubtitulo = new Label("Painel do Aluno");
        lblSubtitulo.getStyleClass().add("subtitle");

        //------------------------------------
        // BOTÕES
        //------------------------------------

        Button btnViewGrades =
                criarBotao("Visualizar Minhas Notas");

        Button btnLogout = new Button("Logout");

        btnLogout.setMaxWidth(Double.MAX_VALUE);
        btnLogout.getStyleClass().add("logout-button");

        //------------------------------------
        // EVENTOS
        //------------------------------------

        btnViewGrades.setOnAction(e -> {

            System.out.println(
                    "[Em breve] Visualização gráfica das notas."
            );

        });

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

                btnViewGrades,

                spacer,

                btnLogout

        );

        //------------------------------------
        // ROOT
        //------------------------------------

        StackPane root = new StackPane(card);

        root.setPadding(new Insets(30));

        Scene scene = new Scene(root, 600, 550);

        scene.getStylesheets().add(
                getClass()
                        .getResource("/css/style.css")
                        .toExternalForm()
        );

        stage.getIcons().add(
                new Image(
                        getClass().getResourceAsStream("/images/logo.png")
                )
        );

        stage.setTitle("Sistema Acadêmico");

        stage.setScene(scene);
    }

    /**
     * Cria um botão padronizado.
     */
    private Button criarBotao(String texto) {

        Button button = new Button(texto);

        button.setMaxWidth(Double.MAX_VALUE);

        button.getStyleClass().add("menu-button");

        return button;
    }

}
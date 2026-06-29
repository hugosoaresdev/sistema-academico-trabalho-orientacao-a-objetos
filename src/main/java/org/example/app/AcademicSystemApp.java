package org.example.app;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.example.controller.AcademicSystemController;
import org.example.domain.Classroom;
import org.example.domain.Exam;
import org.example.domain.Teacher;

import java.util.NoSuchElementException;

public class AcademicSystemApp extends Application {

    @Override
    public void start(Stage primaryStage) {

        inicializarSistema();

        configurarJanela(primaryStage);

        AcademicSystemController controller = new AcademicSystemController(primaryStage);
        controller.iniciar();

        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    /**
     * Realiza um teste básico de integridade das classes principais.
     */
    private void inicializarSistema() {

        try {

            Teacher teacher = new Teacher();
            Classroom classroom = new Classroom();
            Exam exam = new Exam();

            classroom.setClassroomTeacher(teacher);
            classroom.adicionaNaListaDeProvas(exam);

        } catch (NullPointerException e) {

            System.out.println("Erro ao inicializar o sistema (NullPointerException).");
            System.exit(1);

        } catch (IllegalArgumentException e) {

            System.out.println("Erro ao inicializar o sistema (IllegalArgumentException).");
            System.exit(1);

        } catch (NoSuchElementException e) {

            System.out.println("Erro ao inicializar o sistema (NoSuchElementException).");
            System.exit(1);

        }

    }

    /**
     * Configura propriedades da janela principal.
     */
    private void configurarJanela(Stage stage) {

        stage.setTitle("Sistema Acadêmico");

        stage.setMinWidth(900);
        stage.setMinHeight(650);

        try {

            stage.getIcons().add(
                    new Image(
                            getClass().getResourceAsStream("/images/logo.png")
                    )
            );

        } catch (Exception ignored) {
            // Caso a logo ainda não exista.
        }

    }

    public static void main(String[] args) {
        launch(args);
    }

}
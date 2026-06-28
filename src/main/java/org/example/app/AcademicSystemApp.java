package org.example.app;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.controller.AcademicSystemController;
import org.example.domain.Classroom;
import org.example.domain.Exam;
import org.example.domain.Teacher;

import java.util.NoSuchElementException;

public class AcademicSystemApp extends Application {

    @Override
    public void start(Stage primaryStage) {

        verificarIntegridade();

        AcademicSystemController controller =
                new AcademicSystemController(primaryStage);

        controller.iniciar();
        primaryStage.show();
    }

    /**
     * Executa verificações básicas das entidades antes da inicialização
     * da interface gráfica.
     */
    private void verificarIntegridade() {

        try {

            Teacher teacher = new Teacher();
            Classroom classroom = new Classroom();
            Exam exam = new Exam();

            classroom.setClassroomTeacher(teacher);
            classroom.adicionaNaListaDeProvas(exam);

        } catch (NullPointerException e) {

            encerrarAplicacao(
                    "Erro de inicialização: referência nula encontrada."
            );

        } catch (IllegalArgumentException e) {

            encerrarAplicacao(
                    "Erro de inicialização: argumento inválido."
            );

        } catch (NoSuchElementException e) {

            encerrarAplicacao(
                    "Erro de inicialização: elemento inexistente."
            );
        }
    }

    /**
     * Exibe a mensagem de erro e encerra a aplicação.
     */
    private void encerrarAplicacao(String mensagem) {

        System.err.println(mensagem);
        System.exit(1);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
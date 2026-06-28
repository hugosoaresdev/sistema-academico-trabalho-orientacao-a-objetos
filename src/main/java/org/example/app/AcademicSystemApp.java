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
    public void start(Stage primaryStage){

        // --- 1. TESTES DE INTEGRIDADE ---
        try {
            //Testando integridade das classes
            Teacher teacher = new Teacher();
            Classroom classroom = new Classroom();
            Exam exam = new Exam();

            classroom.setClassroomTeacher(teacher);
            classroom.adicionaNaListaDeProvas(exam);

        } catch (NullPointerException e) {
            System.out.println("Erro ao inicializar sistema, tentativa de acessar variável nula (NullPointerException)");
            System.exit(1);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao inicializar sistema, método inválido passado como argumento (IllegalArgumentException)");
            System.exit(1);
        } catch (NoSuchElementException e) {
            System.out.println("Erro ao inicializar sistema, o sistema está tentando ler algo que não existe (NoSuchElementException)");
            System.exit(1);
        }

        // --- 2. INICIALIZANDO INFRAESTRUTURA GRÁFICA ---

        AcademicSystemController controller = new AcademicSystemController(primaryStage);
        controller.iniciar();
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}

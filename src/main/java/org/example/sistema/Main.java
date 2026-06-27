package org.example.sistema;

import org.example.controller.AcademicSystemController;
import org.example.domain.Classroom;
import org.example.domain.Exam;
import org.example.domain.Teacher;

import java.util.NoSuchElementException;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private static final Logger logger =
            LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        logger.info("Infraestrutura de logs funcionando.");

        //TESTANDO INICIAÇÃO CORRETA DO SISTEMA

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

        //INICIALIZANDO O CONTROLADOR DO SISTEMA

        AcademicSystemController controller = new AcademicSystemController();
        controller.iniciar();

    }
}

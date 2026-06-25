package org.example.sistema;

import org.example.domain.Classroom;
import org.example.domain.Exam;
import org.example.domain.Teacher;
import org.example.menu.AdministratorMenu;
import org.example.menu.StudentMenu;
import org.example.menu.TeacherMenu;
import org.example.security.Authenticate;
import org.example.security.AuthorizationException;
import org.example.security.AuthenticationException;
import org.example.security.User;
import org.example.exception.KeyboardInputException;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        //TESTANDO INICIAÇÃO CORRETA DO SISTEMA

        try {
            //testando conexão com Banco de Dados
            //          código
            //===========================================

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

        //INICIALIZANDO VARIÁVEIS E MENU DO SISTEMA

        Scanner input = new Scanner(System.in);
        Authenticate authenticate = new Authenticate();
        boolean running = true;

        while (running) {

            // TELA DE LOGIN
            System.out.println("=========================================");
            System.out.println("         ACADEMIC SYSTEM - LOGIN         ");
            System.out.println("=========================================");
            System.out.print("Email: ");
            String email = input.nextLine();

            System.out.print("Senha: ");
            String senha = input.nextLine();

            User currentUser;
            try {
                currentUser = authenticate.login(email, senha);
                System.out.println("Bem-vindo, " + currentUser.getUsername()
                        + " [" + currentUser.getRole() + "]");
            } catch (AuthenticationException e) {
                System.out.println("Falha no login: " + e.getMessage());
                continue;
            } catch (IOException e) {
                System.out.println("Erro ao ler arquivo de usuários: " + e.getMessage());
                continue;
            }

            // MENU PRINCIPAL
            boolean sessionRunning = true;

            while (sessionRunning) {
                System.out.println("=========================================");
                System.out.println("       WELCOME TO ACADEMIC SYSTEM        ");
                System.out.println("=========================================");
                System.out.println("1. Menu Principal");
                System.out.println("0. Logout");
                System.out.println("=========================================");
                System.out.print("Choose an option: ");

                int option = -1;
                try {
                    option = input.nextInt();
                    input.nextLine();
                } catch (InputMismatchException e) {
                    // US-2368 - AC1 e AC6: número inválido vira KeyboardInputException,
                    // tratado aqui mesmo sem encerrar o app.
                    KeyboardInputException kbEx = new KeyboardInputException(
                            "Entrada inválida: era esperado um número inteiro.", e);
                    System.out.println(kbEx.getMessage());
                    input.nextLine();
                    continue;
                }

                switch (option) {
                    case 1:
                        if (currentUser.isAdmin()) {
                            new AdministratorMenu(currentUser).carregarMenuAdmin(input);
                        } else if (currentUser.isTeacher()) {
                            new TeacherMenu().carregarMenuProfessor(input);
                        } else if (currentUser.isStudent()) {
                            new StudentMenu().carregarMenuEstudante(input);
                        }
                        break;
                    case 0:
                        // US-2379: encerra sessão e volta para o login
                        System.out.println("Logout realizado. Até logo, "
                                + currentUser.getUsername() + "!");
                        sessionRunning = false;
                        break;
                    default:
                        // US-2368 - AC2: opção de menu não suportada também é
                        // um erro de entrada de teclado, então usamos a mesma exceção.
                        KeyboardInputException menuEx = new KeyboardInputException(
                                "Opção de menu inválida: " + option);
                        System.out.println(menuEx.getMessage());
                        break;
                }
            }
        }
    }
}

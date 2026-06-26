package org.example.sistema;

import org.example.domain.Classroom;
import org.example.domain.Exam;
import org.example.domain.Teacher;
import org.example.menu.AdministratorMenu;
import org.example.menu.StudentMenu;
import org.example.menu.TeacherMenu;
import org.example.security.Authenticate;
import org.example.security.AuthenticationExceptionAcademic;
import org.example.security.User;

import java.io.IOException;

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
            } catch (AuthenticationExceptionAcademic e) {
                System.out.println("Falha no login: " + e.getMessage());
                continue;
            } catch (IOException e) {
                System.out.println("Erro ao ler arquivo de usuários: " + e.getMessage());
                continue;
            }

            // MENU POR FUNÇÃO (US-2378 / US-2380)
            // Em vez de um menu intermediário, chamamos direto o menu da
            // função do usuário autenticado. Cada menu mostra apenas as
            // opções da sua função, numeradas a partir de 1.
            if (currentUser.isAdmin()) {
                new AdministratorMenu(currentUser).carregarMenuAdmin(input);
            } else if (currentUser.isTeacher()) {
                new TeacherMenu(currentUser).carregarMenuProfessor(input);
            } else if (currentUser.isStudent()) {
                new StudentMenu(currentUser).carregarMenuEstudante(input);
            }

            // US-2379: ao sair do menu da função, a sessão é encerrada
            // e voltamos para a tela de login.
            System.out.println("Logout realizado. Até logo, "
                    + currentUser.getUsername() + "!");
        }
    }
}

package org.example.sistema;

import org.example.domain.Classroom;
import org.example.domain.Exam;
import org.example.domain.Teacher;
import org.example.menu.AdministratorMenu;
import org.example.menu.StudentMenu;
import org.example.menu.TeacherMenu;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args){

        //TESTANDO INICIAÇÃO CORRETA DO SISTEMA

        try{
            //inicialização dos modelos de domínio
            Teacher teacher = new Teacher();
            Classroom classroom = new Classroom();
            Exam exam = new Exam();

            classroom.setClassTeacher(teacher);
            classroom.adicionaNaListaDeProvas(exam);
        }
        catch (NullPointerException e){
            System.out.println("Erro ao inicializar sistema, tentativa de acessar variável nula (NullPointerException)");
            System.exit(1);
        }
        catch (IllegalArgumentException e){
            System.out.println("Erro ao inicializar sistema, método inválido passado como argumento (IllegalArgumentException)");
            System.exit(1);
        }
        catch (NoSuchElementException e){
            System.out.println("Erro ao inicializar sistema, o sistema está tentando ler algo que não existe (NoSuchElementException)");
            System.exit(1);
        }

        //INICIALIZANDO VARIÁVEIS E MENU DO SISTEMA

        Scanner input = new Scanner(System.in);
        boolean running = true;

        TeacherMenu teacherMenu = new TeacherMenu();
        StudentMenu studentMenu = new StudentMenu();
        AdministratorMenu administratorMenu = new AdministratorMenu();

        while(running) {
            System.out.println("=========================================");
            System.out.println("       WELCOME TO ACADEMIC SYSTEM        ");
            System.out.println("=========================================");
            System.out.println("Who are you?");
            System.out.println("1. Administrator");
            System.out.println("2. Teacher");
            System.out.println("3. Student");
            System.out.println("0. Exit System");
            System.out.println("=========================================");
            System.out.print("Choose your profile: ");

            int option = -1;

            try {
                option = input.nextInt();
                input.nextLine(); // limpa o buffer do enter
            }
            catch (InputMismatchException e) {
                System.out.println("Erro! Você deve digitar um número inteiro!");
                input.nextLine();
                continue;
            }

            switch (option) {
                case 1:
                    administratorMenu.carregarMenuAdmin(input);
                    break;
                case 2:
                    teacherMenu.carregarMenuProfessor(input);
                    break;
                case 3:
                    studentMenu.carregarMenuEstudante(input);
                    break;
                case 0:
                    System.out.println("Exiting...");
                    running = false;
                    break;
                default:
                    System.out.println("Inválid option!");
                    break;
            }

        }

    }
}


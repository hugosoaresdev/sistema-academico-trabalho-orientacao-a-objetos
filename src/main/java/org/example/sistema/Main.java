package org.example.sistema;

import org.example.domain.Classroom;
import org.example.domain.Exam;
import org.example.domain.Teacher;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args){

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

        boolean running = true;
        Scanner input = new Scanner(System.in);

        while(running){
            System.out.println("=========================================");
            System.out.println("       ACADEMIC SYSTEM - MAIN MENU       ");
            System.out.println("=========================================");
            System.out.println("1. Manage Classrooms");
            System.out.println("2. Manage Exams");
            System.out.println("3. View Reports");
            System.out.println("0. Exit System");
            System.out.println("=========================================");
            System.out.println("Chose an option: ");

            int option = -1;

            try{
                option = input.nextInt();
                input.nextLine(); // limpa o buffer do enter
            }
            catch (InputMismatchException e){
                System.out.println("Erro! Você deve digitar um número inteiro!");
                input.nextLine();
                continue;
            }

            switch(option){
                case 1:
                    System.out.println("=========================================");
                    System.out.println("      List of available classrooms       ");
                    System.out.println("=========================================");
                    break;
                case 2:
                    System.out.println("=========================================");
                    System.out.println("      List of available exams            ");
                    System.out.println("=========================================");
                    break;
                case 3:
                    System.out.println("=========================================");
                    System.out.println("                Reports                  ");
                    System.out.println("=========================================");
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

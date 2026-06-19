package org.example.menu;

import java.util.InputMismatchException;
import java.util.Scanner;

public class TeacherMenu {

    public void carregarMenuProfessor(Scanner input){

        boolean subRunning = true;

        while(subRunning) {
            System.out.println("=========================================");
            System.out.println("     ACADEMIC SYSTEM - TEACHER MENU      ");
            System.out.println("=========================================");
            System.out.println("1. Manage Classrooms");
            System.out.println("2. Manage Exams");
            System.out.println("3. View Reports");
            System.out.println("0. Exit System");
            System.out.println("=========================================");
            System.out.println("Chose an option: ");

            int option = -1;

            try {
                option = input.nextInt();
                input.nextLine(); // limpa o buffer do enter
            } catch (InputMismatchException e) {
                System.out.println("Erro! Você deve digitar um número inteiro!");
                input.nextLine();
                continue;
            }

            switch (option) {
                case 1:
                    System.out.println("--- Exibing Classrooms Info ---");
                    break;
                case 2:
                    System.out.println("--- Exibing Exams Info ---");
                    break;
                case 3:
                    System.out.println("--- Exibing Reports ---");
                    break;
                case 0:
                    System.out.println("Exiting to Main Menu...");
                    subRunning = false;
                    break;
                default:
                    System.out.println("Inválid option!");
                    break;
            }
        }
    }
}

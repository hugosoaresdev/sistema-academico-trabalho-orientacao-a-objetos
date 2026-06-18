package org.example.menu;

import java.util.InputMismatchException;
import java.util.Scanner;

public class StudentMenu {

    public void carregarMenuEstudante(Scanner input){

        boolean subRunning = true;

        while(subRunning) {
            System.out.println("=========================================");
            System.out.println("     ACADEMIC SYSTEM - STUDENT MENU      ");
            System.out.println("=========================================");
            System.out.println("1. View Grades");
            System.out.println("2. View Upcoming Exams");
            System.out.println("3. View Classroom Info");
            System.out.println("0. Back to Main Menu");
            System.out.println("=========================================");
            System.out.print("Choose an option: ");

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
                    System.out.println("--- Listing Student Grades ---");;
                    break;
                case 2:
                    System.out.println("--- Listing Upcoming Exams ---");
                    break;
                case 3:
                    System.out.println("--- Exibing Classroom Info ---");
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

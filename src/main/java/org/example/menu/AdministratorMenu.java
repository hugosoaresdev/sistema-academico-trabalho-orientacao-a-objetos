package org.example.menu;

import java.util.InputMismatchException;
import java.util.Scanner;

public class AdministratorMenu {

    public void carregarMenuAdmin(Scanner input){

        boolean subRunning = true;

        while(subRunning) {
            System.out.println("=========================================");
            System.out.println("   ACADEMIC SYSTEM - ADMINISTRATOR MENU  ");
            System.out.println("=========================================");
            System.out.println("1. Register New Teacher");
            System.out.println("2. Register New Student");
            System.out.println("3. Create New Classroom");
            System.out.println("4. Enroll Student in Class");
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
                    System.out.println("\n--- Registering Teacher ---");
                    break;
                case 2:
                    System.out.println("--- Registering Student ---");
                    break;
                case 3:
                    System.out.println("--- Registering Classroom ---");
                    break;
                case 4:
                    System.out.println("--- Registering Student in Classroom ---");
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

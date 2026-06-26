package org.example.menu;

import org.example.exception.KeyboardInputException;
import org.example.security.User;

import java.util.InputMismatchException;
import java.util.Scanner;

public class StudentMenu {

    private final User currentUser;

    public StudentMenu(User currentUser) {
        this.currentUser = currentUser;
    }

    /**
     * US-2378 / US-2380 - Menu do ESTUDANTE.
     *
     * Mostra apenas as opções da função STUDENT, numeradas a partir
     * de 1. Operações de ADMIN/PROFESSOR não aparecem aqui.
     *
     * Opções ainda não implementadas aparecem como "[Em breve]".
     */
    public void carregarMenuEstudante(Scanner input) {

        boolean subRunning = true;

        while (subRunning) {
            System.out.println("=========================================");
            System.out.println("     ACADEMIC SYSTEM - STUDENT MENU      ");
            System.out.println("=========================================");
            System.out.println("1. View My Grades");
            System.out.println("0. Logout");
            System.out.println("=========================================");
            System.out.print("Choose an option: ");

            int option;
            try {
                option = input.nextInt();
                input.nextLine();
            } catch (InputMismatchException e) {
                KeyboardInputException kbEx = new KeyboardInputException(
                        "Entrada inválida: você deve digitar um número inteiro.", e);
                System.out.println(kbEx.getMessage());
                input.nextLine();
                continue;
            }

            switch (option) {
                case 1 -> System.out.println(
                        "\n[Em breve] Visualização das suas notas.");
                case 0 -> {
                    System.out.println("Saindo...");
                    subRunning = false;
                }
                default -> {
                    KeyboardInputException menuEx = new KeyboardInputException(
                            "Opção de menu inválida: " + option);
                    System.out.println(menuEx.getMessage());
                }
            }
        }
    }
}
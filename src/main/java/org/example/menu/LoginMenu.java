package org.example.menu;

import java.util.Scanner;

public class LoginMenu {

    private final Scanner input;

    public LoginMenu(Scanner input) {
        this.input = input;
    }

    public String[] pedirCredenciais() {
        System.out.println("=========================================");
        System.out.println("         ACADEMIC SYSTEM - LOGIN         ");
        System.out.println("=========================================");
        System.out.print("Email: ");
        String email = input.nextLine();

        System.out.print("Senha: ");
        String senha = input.nextLine();

        return new String[]{email, senha};
    }
}

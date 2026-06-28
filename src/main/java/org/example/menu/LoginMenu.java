package org.example.menu;

import java.util.Scanner;

/**
 * Responsável por exibir o menu de autenticação no modo texto
 * e coletar as credenciais informadas pelo usuário.
 */
public class LoginMenu {

    private final Scanner input;

    public LoginMenu(Scanner input) {
        this.input = input;
    }

    /**
     * Solicita as credenciais de acesso ao usuário.
     *
     * @return um vetor contendo:
     *         posição 0 -> e-mail
     *         posição 1 -> senha
     */
    public String[] pedirCredenciais() {

        exibirCabecalho();

        System.out.print("E-mail: ");
        String email = input.nextLine();

        System.out.print("Senha: ");
        String senha = input.nextLine();

        return new String[]{email, senha};
    }

    /**
     * Exibe o cabeçalho do sistema.
     */
    private void exibirCabecalho() {
        System.out.println("=========================================");
        System.out.println("         SISTEMA ACADÊMICO");
        System.out.println("=========================================");
        System.out.println("Autenticação");
        System.out.println();
    }
}
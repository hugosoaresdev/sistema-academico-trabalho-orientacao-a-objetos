package org.example.controller;

import org.example.menu.LoginMenu;
import org.example.security.Authenticate;
import org.example.security.AuthenticationExceptionAcademic;
import org.example.security.User;

import java.io.IOException;
import java.util.Scanner;

public class AcademicSystemController {

    private Scanner input;
    private Authenticate authenticate;
    private boolean running;

    public AcademicSystemController(){
        this.input = new Scanner(System.in);
        this.authenticate = new Authenticate();
        this.running = true;
    }

    //Metodo principal que inicia o loop do sistema

    public void iniciar(){
        while (running){
            User currentUser = realizarLogin();

            if(currentUser != null) {
                direcionarParaMenu(currentUser);
                System.out.println("Logout realizado. Até logo, " + currentUser.getUsername() + "!");
            }
        }
    }

    // Cuida estritamente da tela e validação do login

    public User realizarLogin() {

        LoginMenu currentUser = new LoginMenu(input);
        String[] credenciais = currentUser.pedirCredenciais();

        try {
            User user = authenticate.login(credenciais[0], credenciais[1]);
            System.out.println("Bem-vindo, " + user.getUsername() + " [" + user.getRole() + "]");
            return user;
        } catch (AuthenticationExceptionAcademic e) {
            System.out.println("Falha no login: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Erro ao ler arquivo de usuários: " + e.getMessage());
        }
        return null;
    }

    // Substitui a sequência de ifs e direciona para o menu correto

    public void direcionarParaMenu(User currentUser){
        currentUser.getMenu().carregarMenu(input);
    }
}

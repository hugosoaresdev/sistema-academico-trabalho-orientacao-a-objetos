package org.example.controller;

import javafx.stage.Stage;
import org.example.app.LoginMenuApp;
import org.example.security.Authenticate;
import org.example.security.AuthenticationExceptionAcademic;
import org.example.security.User;

import java.io.IOException;

public class AcademicSystemController {

    private final Stage stage;
    private Authenticate authenticate;

    public AcademicSystemController(Stage stage){
        this.stage = stage;
        this.authenticate = new Authenticate();
    }

    // O metodo iniciar agora não tem while loop. Ele só joga a tela de login na janela.
    public void iniciar(){
        System.out.println("Iniciando interface gráfica...");
        mostrarTelaLogin();
    }

    // Metodo para exibir a tela de login
    public void mostrarTelaLogin() {
        LoginMenuApp loginView = new LoginMenuApp(stage, this);
        loginView.exibir();
    }


    public User processarLogin(String email, String senha) {
        try{
            User user = authenticate.login(email, senha);
            System.out.println("Bem-vindo, " + user.getUsername() + " [" + user.getRole() + "]");

            // Em vez de retornar para o while, nós já mandamos direcionar para o menu aqui!
            direcionarParaMenu(user);
            return user;

        } catch (AuthenticationExceptionAcademic e) {
            // Vamos reolançar a exceção ou tratar para a tela mostrar o erro em texto vermelho
            throw new RuntimeException("Falha no login: " + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler arquivo: " + e.getMessage());
    }
}

    public void direcionarParaMenu(User currentUser){
        currentUser.getMenuApp().carregarMenu(this.stage,this);
    }
}

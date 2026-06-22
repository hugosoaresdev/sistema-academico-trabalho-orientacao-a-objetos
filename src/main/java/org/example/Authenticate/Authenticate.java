package org.example.Authenticate;

import org.example.domain.Student;
import org.example.domain.Teacher;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Authenticate {

    public enum Role {
        STUDENT, //valor 0
        TEACHER, //valor 1
        ADMIN    //valor 2
    }
    //AC1 - AC2
    // ==========================================
    // leitura de usuarios de login
    // ==========================================

    private ArrayList<String> loginUsers(String path) throws IOException {
        ArrayList<String> users = new ArrayList<>();

        BufferedReader br = new BufferedReader(new FileReader(path));
        String linha;

        while ((linha = br.readLine()) != null) {
            users.add(linha);
        }

        br.close();
        return users;
    }

    // ==========================================
    // AUTORIZAÇÃO de login
    // ==========================================
    public boolean login(String user, String password) throws IOException {
        String caminhoDoArquivo = "src/main/resources/usuarios.txt";

        ArrayList<String> listaDeUsuarios = loginUsers(caminhoDoArquivo);

        for (String linha : listaDeUsuarios) {
            String[] dados = linha.split(";");

            String emailCadastrado = dados[0];
            String senhaCadastrada = dados[1];

            // AC1: Se encontrar o usuário com as credenciais corretas
            if (emailCadastrado.equals(user)){
                if(senhaCadastrada.equals(password))
                {
                    return true;
                }

                throw new AuthException("Erro, senha incorreta!");
            }
        }
        throw new AuthException("Erro, email incorreto!");
    }


    //AC3 - AC4
    // ==========================================
    // AUTORIZAÇÃO POR NICHOS (Sobrecarga de Métodos)
    // ==========================================

    public void checkAuthorize(Teacher teacher, Role cargoExigido) {
        if (cargoExigido.ordinal() >=  Role.TEACHER.ordinal()) {
            throw new AuthException("ACESSO NEGADO: usuario não tem permissão");
        }
    }

}
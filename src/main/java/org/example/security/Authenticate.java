package org.example.security;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Authenticate {

    // TODO (US-2366): tornar o arquivo de usuários configurável via properties.

    // ==========================================
    // AC1 e AC2 da US-2366
    // Leitura de usuários do arquivo TXT (AC5)
    // ==========================================

    // Método privado auxiliar: lê o arquivo de usuários linha por linha
    // e retorna uma lista de strings brutas (cada linha = um usuário).
    // Usa classpath em vez de caminho relativo, garantindo que funciona
    // tanto no IntelliJ quanto em qualquer outro ambiente (Docker, CI, etc).
    // Referência: AC5 da US-2366 — "dados de autenticação devem ser
    // carregados do repositório de usuários TXT configurado".
    private ArrayList<String> loginUsers() throws IOException {
        ArrayList<String> users = new ArrayList<>();

        InputStream is = getClass().getClassLoader()
                .getResourceAsStream("usuarios.txt");

        if (is == null) {
            throw new IOException("Arquivo usuarios.txt não encontrado no classpath.");
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String linha;

        while ((linha = br.readLine()) != null) {
            if (!linha.isBlank()) {
                users.add(linha.trim());
            }
        }

        br.close();
        return users;
    }

    // ==========================================
    // AC1 e AC2 da US-2366
    // Autenticação por email e senha
    // ==========================================

    // Método público de login: recebe email e senha digitados pelo usuário,
    // compara com as linhas do arquivo TXT e retorna um User autenticado
    // com sua Role. Lança AuthException se email ou senha estiverem errados.
    //
    // Formato esperado de cada linha do arquivo:
    //   email;senha;ROLE
    // Exemplo:
    //   admin@sistema.com;admin123;ADMIN
    //
    // ATENÇÃO: senha nunca deve aparecer em mensagens de log (AC6 da US-2366).
    public User login(String email, String password) throws IOException {
        ArrayList<String> listaDeUsuarios = loginUsers();

        for (String linha : listaDeUsuarios) {
            // Quebra a linha pelo separador ";" para separar email, senha e role
            String[] dados = linha.split(";");

            // Ignora linhas mal formatadas
            if (dados.length < 3) continue;

            String emailCadastrado = dados[0];
            String senhaCadastrada = dados[1];
            String roleCadastrada  = dados[2];

            if (emailCadastrado.equals(email)) {

                // Senha incorreta — não inclui a senha na mensagem (AC6)
                if (!senhaCadastrada.equals(password)) {
                    throw new AuthException("Erro: senha incorreta.");
                }

                // Converte a string do arquivo para o enum Role
                Role role;
                try {
                    role = Role.valueOf(roleCadastrada.toUpperCase());
                } catch (IllegalArgumentException e) {
                    throw new AuthException(
                            "Erro: role inválida no arquivo de usuários: " + roleCadastrada);
                }

                // Retorna o usuário autenticado com sua role (AC1)
                return new User(email, role);
            }
        }

        // Email não encontrado (AC2)
        throw new AuthException("Erro: email não encontrado.");
    }

    // ==========================================
    // AC3 e AC4 da US-2366
    // Autorização por role
    // ==========================================

    // Checa se o usuário autenticado tem a role exigida.
    // Lança AuthException se não tiver permissão (AC4).
    // Unificado num único método — funciona para qualquer role,
    // sem precisar de sobrecarga por tipo de usuário.
    public void checkAuthorize(User user, Role roleExigida) {
        if (user.getRole() != roleExigida) {
            throw new AuthException(
                    "ACESSO NEGADO: operação exige role " + roleExigida
                            + ", mas usuário tem role " + user.getRole() + ".");
        }
    }
}
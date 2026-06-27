package org.example.security;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Authenticate {

    private static final Logger logger =
            LoggerFactory.getLogger(Authenticate.class);

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
// com sua Role. Lança AuthenticationException se email ou senha estiverem errados.
    //
    // Formato esperado de cada linha do arquivo:
    //   email;senha;ROLE
    // Exemplo:
    //   admin@sistema.com;admin123;ADMIN
    //
    // ATENÇÃO: senha nunca deve aparecer em mensagens de log (AC6 da US-2366).
    public User login(String email, String password) throws IOException {

        logger.info("Tentativa de autenticação para o usuário: {}", email);

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
                    logger.warn("Falha na autenticação: senha incorreta para o usuário: {}", email);
                    throw new AuthenticationExceptionAcademic("Erro: senha incorreta.");
                }

                // Converte a string do arquivo para o enum Role
                Role role;
                try {
                    role = Role.valueOf(roleCadastrada.toUpperCase());
                } catch (IllegalArgumentException e) {
                    logger.error("Role inválida encontrada para o usuário: {}", email, e);
                    throw new AuthenticationExceptionAcademic(
                            "Erro: role inválida no arquivo de usuários: " + roleCadastrada);
                }

                // Retorna o usuário autenticado com sua role (AC1)
                logger.info("Usuário autenticado com sucesso: {}", email, role);
                return new User(email, role);
            }
        }

        // Email não encontrado (AC2)
        logger.warn("Falha na autenticação: usuário não encontrado: {}", email);
        throw new AuthenticationExceptionAcademic("Erro: email não encontrado.");
    }

    // ==========================================
    // AC3 e AC4 da US-2366
    // Autorização por role
    // ==========================================

    // Checa se o usuário autenticado tem a role exigida.
    // Lança AuthorizationException se não tiver permissão (AC4).
    // Unificado num único método — funciona para qualquer role,
    // sem precisar de sobrecarga por tipo de usuário.
    public void checkAuthorize(User user, Role roleExigida) {
        if (user.getRole().ordinal() < roleExigida.ordinal()) {

            logger.warn(
                    "Acesso negado para o usuário {}. Role atual: {}. Role necessária: {}",
                    user.getUsername(),
                    user.getRole(),
                    roleExigida
            );

            throw new AuthorizationExceptionAcademic(
                    "ACESSO NEGADO: operação exige role " + roleExigida
                            + ", mas usuário tem role " + user.getRole() + ".");
        }
    }
}
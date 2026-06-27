package org.example.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.menu.*;

/**
 * Representa o usuário autenticado no sistema.
 *
 * Criado após autenticação bem-sucedida pelo Authenticate.login(),
 * e passado para os menus para que possam checar permissões.
 *
 * TODO (US-2366): expandir conforme necessário quando a autenticação
 * completa for implementada (ex: adicionar credenciais, sessão, etc).
 */

@Getter
@AllArgsConstructor
public class User {

    private String username;
    private Role role;

    public boolean isAdmin() {
        return this.role == Role.ADMIN;
    }

    public boolean isTeacher() {
        return this.role == Role.TEACHER;
    }

    public boolean isStudent() {
        return this.role == Role.STUDENT;
    }

    //Simplifica a descoberta do menu por meio de interface

    public Menu getMenu() {
        return switch (this.role) {
            case ADMIN -> new AdministratorMenu(this);
            case TEACHER -> new TeacherMenu(this);
            case STUDENT -> new StudentMenu(this);
        };
    }
}
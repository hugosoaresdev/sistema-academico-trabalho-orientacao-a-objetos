package org.example.menu;


import org.example.domain.Classroom;
import org.example.domain.Teacher;
import org.example.exception.AcademicSystemException;
import org.example.exception.KeyboardInputException;
import org.example.security.User;
import org.example.service.ClassService;
import org.example.sistema.AcademicSystem;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;


public class AdministratorMenu {


    // TUS-2396: validação e registro agora ficam no ClassService,
    // o menu só monta os dados lidos do teclado e delega.
    private final ClassService classService = new ClassService();
    private final User currentUser;

    public AdministratorMenu(User currentUser) {
        this.currentUser = currentUser;
    }
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
            System.out.println("5. List Classrooms"); //Novo
            System.out.println("0. Back to Main Menu");
            System.out.println("=========================================");
            System.out.print("Choose an option: ");

            int option = -1;

            try {
                option = input.nextInt();
                input.nextLine(); // limpa o buffer do enter
            } catch (InputMismatchException e) {
                // US-2368 - AC1 e AC6: erro de teclado tratado aqui mesmo,
                // sem deixar o app cair, usando a exceção própria de teclado.
                KeyboardInputException kbEx = new KeyboardInputException(
                        "Entrada inválida: você deve digitar um número inteiro.", e);
                System.out.println(kbEx.getMessage());
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
                    // US-2363: antes só imprimia uma mensagem, agora delega
                    // para o método que de fato registra a turma.
                    registrarTurma(input);
                    break;
                case 4:
                    System.out.println("--- Registering Student in Classroom ---");
                    break;
                case 5:                    // Adição das Lista de turma
                    listarTurmas();
                    break;
                case 0:
                    System.out.println("Exiting to Main Menu...");
                    subRunning = false;
                    break;
                default:
                    // US-2368 - AC2: opção de menu não suportada também é
                    // um erro de entrada de teclado.
                    KeyboardInputException menuEx = new KeyboardInputException(
                            "Opção de menu inválida: " + option);
                    System.out.println(menuEx.getMessage());
                    break;
            }
        }
    }

    /**
     * US-2363 - Registrar turma via entrada de teclado.
     *
     * Fluxo: verifica se o usuário é ADMIN, lê os dados da turma e do
     * professor via teclado, monta o objeto e delega a validação e o
     * registro ao ClassService (TUS-2396). Erros de digitação (ex:
     * código não numérico) lançam KeyboardInputException; dados
     * inválidos ou turma duplicada lançam AcademicSystemException —
     * os dois são tratados sem encerrar o menu.
     */

    private void registrarTurma(Scanner input) {
        System.out.println("\n--- Registering Classroom ---");

        // AC1/AC5: nega a operação se o usuário não for ADMIN
        if (!currentUser.isAdmin()) {
            System.out.println("Acesso negado: apenas administradores podem registrar turmas.");
            return;
        }

        try {
            // Lê o código da turma e converte para número.
            System.out.print("Código da turma (numérico): ");
            Long classroomID;
            try {
                classroomID = Long.parseLong(input.nextLine().trim());
            } catch (NumberFormatException e) {
                throw new KeyboardInputException(
                        "Código de turma inválido: deve ser um número inteiro.", e);
            }

            System.out.print("Título da turma: ");
            String classroomName = input.nextLine();

            // Como ainda não existe a funcionalidade de cadastrar professor
            // separadamente, pedimos os dados do professor aqui mesmo.
            System.out.print("Nome do professor responsável: ");
            String teacherName = input.nextLine();

            System.out.print("Disciplina do professor: ");
            String teacherSubject = input.nextLine();

            System.out.print("Matrícula do professor: ");
            String teacherRegistrationNumber = input.nextLine();

            Teacher teacher = new Teacher(teacherName, teacherSubject, teacherRegistrationNumber);

            // Monta o objeto Classroom com os dados coletados.
            Classroom classroom = new Classroom();
            classroom.setClassroomID(classroomID);
            classroom.setClassroomName(classroomName);
            classroom.setClassroomTeacher(teacher);

            // TUS-2396: validação e registro agora são responsabilidade
            // do ClassService, o menu só delega.
            classService.registerClassroom(classroom);

            System.out.println("Turma registrada com sucesso: " + classroom.getClassroomID());

        } catch (AcademicSystemException e) {
            System.out.println("Erro ao registrar turma: " + e.getMessage());
        } catch (KeyboardInputException e) {
            System.out.println("Erro de entrada: " + e.getMessage());
        }
    }

    private void listarTurmas() {
        System.out.println("\n--- Listing Classrooms ---");

        List<Classroom> classrooms = AcademicSystem.getInstance().getClassrooms();

        if (classrooms.isEmpty()) {
            System.out.println("Nenhuma turma registrada.");
            return;
        }

        for (Classroom classroom : classrooms) {
            System.out.println("------------------------------------------");
            System.out.println("ID:        " + classroom.getClassroomID());
            System.out.println("Título:    " + classroom.getClassroomName());
            System.out.println("Professor: " + classroom.getClassroomTeacher().getName()
                    + " (" + classroom.getClassroomTeacher().getSubject() + ")");
        }
        System.out.println("------------------------------------------");
    }
}

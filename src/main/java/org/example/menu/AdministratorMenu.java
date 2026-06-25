package org.example.menu;

import org.example.domain.Classroom;
import org.example.domain.Teacher;
import org.example.exception.AcademicSystemException;
import org.example.exception.KeyboardInputException;
import org.example.security.User;
import org.example.service.ClassService;

import java.util.InputMismatchException;
import java.util.Scanner;

public class AdministratorMenu {

    private final ClassService classService = new ClassService();
    private final User currentUser;

    public AdministratorMenu(User currentUser) {
        this.currentUser = currentUser;
    }

    public void carregarMenuAdmin(Scanner input) {

        boolean subRunning = true;

        while (subRunning) {
            System.out.println("=========================================");
            System.out.println("   ACADEMIC SYSTEM - ADMINISTRATOR MENU  ");
            System.out.println("=========================================");
            System.out.println("1. Register New Teacher");
            System.out.println("2. Register New Student");
            System.out.println("3. Create New Classroom");
            System.out.println("4. Enroll Student in Class");
            System.out.println("5. List Classrooms");
            System.out.println("0. Back to Main Menu");
            System.out.println("=========================================");
            System.out.print("Choose an option: ");

            int option;

            try {
                option = input.nextInt();
                input.nextLine();
            } catch (InputMismatchException e) {
                System.out.println(
                        "Erro! Você deve digitar um número inteiro!"
                );
                input.nextLine();
                continue;
            }

            switch (option) {
                case 1 -> System.out.println("\n--- Registering Teacher ---");
                case 2 -> System.out.println("\n--- Registering Student ---");
                case 3 -> registrarTurma(input);
                case 4 -> System.out.println(
                        "\n--- Registering Student in Classroom ---"
                );
                case 5 -> System.out.println("\n--- Listing Classrooms ---");
                case 0 -> {
                    System.out.println("Exiting to Main Menu...");
                    subRunning = false;
                }
                default -> System.out.println(
                        "Opção de menu inválida: " + option
                );
            }
        }
    }

    private void registrarTurma(Scanner input) {
        System.out.println("\n--- Registering Classroom ---");

        if (!currentUser.isAdmin()) {
            System.out.println(
                    "Acesso negado: apenas administradores podem registrar turmas."
            );
            return;
        }

        try {
            System.out.print("Código da turma (numérico): ");
            Long classroomID = Long.parseLong(input.nextLine().trim());

            System.out.print("Título da turma: ");
            String classroomName = input.nextLine();

            System.out.print("Nome do professor responsável: ");
            String teacherName = input.nextLine();

            System.out.print("Disciplina do professor: ");
            String teacherSubject = input.nextLine();

            System.out.print("Matrícula do professor: ");
            String teacherRegistrationNumber = input.nextLine();

            Teacher teacher = new Teacher(
                    teacherName,
                    teacherSubject,
                    teacherRegistrationNumber
            );

            Classroom classroom = new Classroom();
            classroom.setClassroomID(classroomID);
            classroom.setClassroomName(classroomName);
            classroom.setClassroomTeacher(teacher);

            classService.registerClassroom(classroom);

            System.out.println(
                    "Turma registrada com sucesso: "
                            + classroom.getClassroomID()
            );

        } catch (NumberFormatException e) {
            System.out.println(
                    "Erro de entrada: código de turma deve ser um número inteiro."
            );
        } catch (KeyboardInputException | AcademicSystemException e) {
            System.out.println("Erro ao registrar turma: " + e.getMessage());
        }
    }
}
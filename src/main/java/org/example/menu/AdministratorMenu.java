package org.example.menu;

import org.example.domain.Classroom;
import org.example.domain.Teacher;
import org.example.exception.AcademicSystemException;
import org.example.exception.KeyboardInputException;
import org.example.security.User;
import org.example.service.ClassService;
import org.example.service.AssessmentService;

import java.util.InputMismatchException;
import java.util.Scanner;

public class AdministratorMenu implements Menu{

    private final ClassService classService = new ClassService();
    private final AssessmentService assessmentService = new AssessmentService();
    private final User currentUser;

    public AdministratorMenu(User currentUser) {
        this.currentUser = currentUser;
    }

    /**
     * US-2378 / US-2380 - Menu do ADMIN.
     *
     * Mostra todas as operações previstas para a função ADMIN,
     * numeradas a partir de 1 (US-2380). As operações que ainda não
     * foram implementadas (de outras histórias) aparecem como
     * "[Em breve]": a estrutura e a numeração do menu já ficam prontas,
     * e cada operação real será ligada à sua opção quando a história
     * correspondente for implementada.
     */

    @Override
    public void carregarMenu(Scanner input) {

        boolean subRunning = true;

        while (subRunning) {
            System.out.println("=========================================");
            System.out.println("   ACADEMIC SYSTEM - ADMINISTRATOR MENU  ");
            System.out.println("=========================================");
            System.out.println("1. Register Classroom");
            System.out.println("2. Register Assessment in Classroom");
            System.out.println("3. List Classrooms");
            System.out.println("4. Save Data to File");
            System.out.println("5. Configure Persistence Type");
            System.out.println("6. Class Assessment Report");
            System.out.println("7. Assessment Weight Report");
            System.out.println("8. Persistence Configuration Report");
            System.out.println("0. Logout");
            System.out.println("=========================================");
            System.out.print("Choose an option: ");

            int option;
            try {
                option = input.nextInt();
                input.nextLine();
            } catch (InputMismatchException e) {
                // US-2368 - AC1 e AC6: número inválido vira KeyboardInputException.
                KeyboardInputException kbEx = new KeyboardInputException(
                        "Entrada inválida: você deve digitar um número inteiro.", e);
                System.out.println(kbEx.getMessage());
                input.nextLine();
                continue;
            }

            switch (option) {
                case 1 -> registrarTurma(input);
                case 2 -> registrarAvaliacao(input);
                case 3 -> System.out.println(
                        "\n[Em breve] Listagem de turmas.");
                case 4 -> System.out.println(
                        "\n[Em breve] Salvar dados em arquivo (TXT/XML/JSON).");
                case 5 -> System.out.println(
                        "\n[Em breve] Configurar tipo de persistência.");
                case 6 -> System.out.println(
                        "\n[Em breve] Relatório resumido de avaliações da turma.");
                case 7 -> System.out.println(
                        "\n[Em breve] Relatório de peso das avaliações.");
                case 8 -> System.out.println(
                        "\n[Em breve] Relatório de configuração de persistência.");
                case 0 -> {
                    System.out.println("Saindo...");
                    subRunning = false;
                }
                default -> {
                    // US-2368 - AC2: opção de menu inválida.
                    KeyboardInputException menuEx = new KeyboardInputException(
                            "Opção de menu inválida: " + option);
                    System.out.println(menuEx.getMessage());
                }
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
    /**
     * US-2361 - Registrar avaliação numa turma existente.
     *
     * O menu só lê os dados do teclado e delega a regra de negócio
     * (achar turma, criar tipo, validar, adicionar) ao AssessmentService
     * (TUS-2397). Erros de digitação viram KeyboardInputException;
     * turma inexistente, tipo inválido ou dados inválidos viram
     * AcademicSystemException — ambos tratados sem encerrar o menu.
     */
    private void registrarAvaliacao(Scanner input) {
        System.out.println("\n--- Registering Assessment ---");

        if (!currentUser.isAdmin()) {
            System.out.println(
                    "Acesso negado: apenas administradores podem registrar avaliações.");
            return;
        }

        try {
            System.out.print("Código da turma (numérico): ");
            Long classroomID;
            try {
                classroomID = Long.parseLong(input.nextLine().trim());
            } catch (NumberFormatException e) {
                throw new KeyboardInputException(
                        "Código de turma inválido: deve ser um número inteiro.", e);
            }

            System.out.println("Tipo de avaliação:");
            System.out.println("  1 - Exame");
            System.out.println("  2 - Tarefa Prática");
            System.out.println("  3 - Seminário");
            System.out.println("  4 - Atribuição");
            System.out.print("Escolha o tipo: ");
            int tipo;
            try {
                tipo = Integer.parseInt(input.nextLine().trim());
            } catch (NumberFormatException e) {
                throw new KeyboardInputException(
                        "Tipo inválido: deve ser um número de 1 a 4.", e);
            }

            System.out.print("Valor da avaliação: ");
            double value;
            double weight;
            try {
                value = Double.parseDouble(input.nextLine().trim());
                System.out.print("Peso da avaliação: ");
                weight = Double.parseDouble(input.nextLine().trim());
            } catch (NumberFormatException e) {
                throw new KeyboardInputException(
                        "Valor/peso inválido: deve ser um número.", e);
            }

            // TUS-2397: toda a regra de negócio fica no service.
            assessmentService.registerAssessment(classroomID, tipo, value, weight);

            System.out.println("Avaliação registrada com sucesso.");

        } catch (AcademicSystemException e) {
            System.out.println("Erro ao registrar avaliação: " + e.getMessage());
        } catch (KeyboardInputException e) {
            System.out.println("Erro de entrada: " + e.getMessage());
        }
    }
}
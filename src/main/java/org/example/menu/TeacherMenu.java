package org.example.menu;

import java.util.InputMismatchException;
import java.util.Scanner;

import org.example.domain.Classroom;
import org.example.exception.AcademicSystemException;
import org.example.exception.KeyboardInputException;
import org.example.service.AssessmentService;
import org.example.sistema.AcademicSystem;

public class TeacherMenu {

    // O menu (garçom) conversa com o teclado e delega a regra de negócio
    // para o service (cozinheiro). US-2361 AC: registro delegado ao service.
    private final AssessmentService assessmentService = new AssessmentService();

    public void carregarMenuProfessor(Scanner input){

        boolean subRunning = true;

        while(subRunning) {
            System.out.println("=========================================");
            System.out.println("     ACADEMIC SYSTEM - TEACHER MENU      ");
            System.out.println("=========================================");
            System.out.println("1. Manage Classrooms");
            System.out.println("2. Register Assessment");
            System.out.println("3. View Reports");
            System.out.println("0. Exit System");
            System.out.println("=========================================");
            System.out.println("Chose an option: ");

            int option = -1;

            try {
                option = input.nextInt();
                input.nextLine(); // limpa o buffer do enter
            } catch (InputMismatchException e) {
                // US-2368 - AC1 e AC6: número inválido vira KeyboardInputException.
                KeyboardInputException kbEx = new KeyboardInputException(
                        "Entrada inválida: você deve digitar um número inteiro.", e);
                System.out.println(kbEx.getMessage());
                input.nextLine();
                continue;
            }

            switch (option) {
                case 1:
                    System.out.println("--- Exibing Classrooms Info ---");
                    break;
                case 2:
                    // US-2361: registrar avaliação em uma turma existente.
                    registrarAvaliacao(input);
                    break;
                case 3:
                    System.out.println("--- Exibing Reports ---");
                    break;
                case 0:
                    System.out.println("Exiting to Main Menu...");
                    subRunning = false;
                    break;
                default:
                    // US-2368 - AC2: opção de menu inválida.
                    KeyboardInputException menuEx = new KeyboardInputException(
                            "Opção de menu inválida: " + option);
                    System.out.println(menuEx.getMessage());
                    break;
            }
        }
    }

    /**
     * US-2361 - Registra uma avaliação em uma turma já existente.
     *
     * Repare na divisão de responsabilidades (a ideia central da US):
     *   - AQUI (menu): só lê os dados do teclado e trata erros de exibição.
     *   - No AssessmentService: toda a regra (achar turma, criar o tipo
     *     certo, validar e adicionar). O menu não conhece nada disso.
     */
    private void registrarAvaliacao(Scanner input) {
        System.out.println("\n--- Registering Assessment ---");

        // Mostra as turmas disponíveis para ajudar o usuário a escolher.
        var turmas = AcademicSystem.getInstance().getClassrooms();
        if (turmas.isEmpty()) {
            System.out.println("Não há turmas cadastradas. Cadastre uma turma antes.");
            return;
        }
        System.out.println("Turmas disponíveis:");
        for (Classroom turma : turmas) {
            System.out.println("  [" + turma.getClassroomID() + "] " + turma.getClassroomName());
        }

        try {
            System.out.print("Código da turma (numérico): ");
            Long classroomID = Long.parseLong(input.nextLine().trim());

            System.out.print("Tipo (Exam, PracticalAssignment, Seminar, Assignment): ");
            String type = input.nextLine().trim();

            System.out.print("ID da avaliação (numérico): ");
            Long assessmentID = Long.parseLong(input.nextLine().trim());

            System.out.print("Título da avaliação: ");
            String title = input.nextLine();

            System.out.print("Valor/nota da avaliação: ");
            double value = Double.parseDouble(input.nextLine().trim().replace(",", "."));

            System.out.print("Peso da avaliação: ");
            double weight = Double.parseDouble(input.nextLine().trim().replace(",", "."));

            // Delega TODA a regra de negócio para o service.
            assessmentService.registerAssessment(
                    classroomID, type, assessmentID, title, value, weight);

            System.out.println("Avaliação registrada com sucesso na turma " + classroomID + "!");

        } catch (NumberFormatException e) {
            // Erro de digitação (texto onde se esperava número).
            System.out.println(
                    "Erro de entrada: código, ID, valor e peso devem ser numéricos.");
        } catch (AcademicSystemException e) {
            // Pega InvalidAssessmentException também, porque ela HERDA de
            // AcademicSystemException (US-2367). Aqui caem: turma inexistente,
            // tipo inválido e dados inválidos.
            System.out.println("Erro ao registrar avaliação: " + e.getMessage());
        }
    }
}
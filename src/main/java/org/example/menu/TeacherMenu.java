package org.example.menu;

import org.example.exception.KeyboardInputException;
import org.example.security.User;
import org.example.service.ReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.InputMismatchException;
import java.util.Scanner;

public class TeacherMenu implements Menu{

    // TUS-2394: logger para auditar a geração de relatórios.
    private static final Logger logger =
            LoggerFactory.getLogger(TeacherMenu.class);

    private final User currentUser;
    private final ReportService reportService = new ReportService();

    public TeacherMenu(User currentUser) {
        this.currentUser = currentUser;
    }

    /**
     * US-2378 / US-2380 - Menu do PROFESSOR.
     *
     * Mostra apenas as opções disponíveis para a função PROFESSOR,
     * numeradas a partir de 1. Operações exclusivas de ADMIN
     * (registrar turma, salvar dados, configurar persistência, etc)
     * NÃO aparecem aqui (US-2378 AC3).
     *
     */
    @Override
    public void carregarMenu(Scanner input) {

        boolean subRunning = true;

        while (subRunning) {
            System.out.println("=========================================");
            System.out.println("     ACADEMIC SYSTEM - TEACHER MENU      ");
            System.out.println("=========================================");
            System.out.println("1. List Classrooms");
            System.out.println("2. View Class Assessment Report");
            System.out.println("3. View Assessment Weight Report");
            System.out.println("0. Logout");
            System.out.println("=========================================");
            System.out.print("Choose an option: ");

            int option;
            try {
                option = input.nextInt();
                input.nextLine();
            } catch (InputMismatchException e) {
                KeyboardInputException kbEx = new KeyboardInputException(
                        "Entrada inválida: você deve digitar um número inteiro.", e);
                System.out.println(kbEx.getMessage());
                input.nextLine();
                continue;
            }

            switch (option) {
                case 1 -> System.out.println(
                        "\n[Em breve] Listagem de turmas.");
                case 2 -> {
                    // TUS-2394: registra a geração, incluindo a função do usuário.
                    logger.info("Relatório gerado: resumo de avaliações | função={}",
                            currentUser.getRole());
                    System.out.println(
                            "\n" + reportService.generateClassAssessmentSummaryReport());
                }
                case 3 -> {
                    // TUS-2394: registra a geração, incluindo a função do usuário.
                    logger.info("Relatório gerado: peso das avaliações | função={}",
                            currentUser.getRole());
                    System.out.println(
                            "\n" + reportService.generateAssessmentWeightReport());
                }
                case 0 -> {
                    System.out.println("Saindo...");
                    subRunning = false;
                }
                default -> {
                    KeyboardInputException menuEx = new KeyboardInputException(
                            "Opção de menu inválida: " + option);
                    System.out.println(menuEx.getMessage());
                }
            }
        }
    }
}
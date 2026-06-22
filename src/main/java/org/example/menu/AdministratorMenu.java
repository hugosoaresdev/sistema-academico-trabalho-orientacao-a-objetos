package org.example.menu;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.example.domain.Classroom;
import org.example.domain.Teacher;
import org.example.exception.AcademicSystemException;
import org.example.sistema.AcademicSystem;
import org.example.security.User;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class AdministratorMenu {

    // Validador do Jakarta Bean Validation, reaproveitando as anotações
    // (@NotBlank, @NotNull) que já existem nas classes de domínio.
    // Criado uma única vez por instância do menu, em vez de recriar a cada chamada.
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
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
                System.out.println("Erro! Você deve digitar um número inteiro!");
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
                    System.out.println("Inválid option!");
                    break;
            }
        }
    }

    /**
     * US-2363 - Matricular-se em aulas através da entrada de teclado.
     *
     * Fluxo: lê os dados da turma e do professor via teclado (AC1),
     * valida (AC4), e armazena no AcademicSystem (AC2).
     * Qualquer problema (entrada inválida, dados inválidos) interrompe
     * o cadastro lançando AcademicSystemException (AC3).
     *
     * TODO (US-2366): falta checar se o usuário autenticado é ADMIN
     * antes de permitir essa operação (AC1/AC5). Hoje não existe
     * autenticação no sistema, então essa checagem ainda não foi feita.
     *
     * TODO (TUS-2396): essa lógica deveria estar num ClassService,
     * não direto aqui no menu. Vamos extrair isso depois.
     */
    private void registrarTurma(Scanner input) {
        System.out.println("\n--- Registering Classroom ---");

        // AC1/AC5: nega a operação se o usuário não for ADMIN
        if (!currentUser.isAdmin()) {
            System.out.println("Acesso negado: apenas administradores podem registrar turmas.");
            return;
        }

        try {
            // Lê o código da turma e já tenta converter pra número.
            // Se o usuário digitar algo que não é número, capturamos aqui
            // e transformamos numa AcademicSystemException por enquanto
            // (no futuro, com a US-2368, isso devia virar KeyboardInputException).
            System.out.print("Código da turma (numérico): ");
            Long classroomID;
            try {
                classroomID = Long.parseLong(input.nextLine().trim());
            } catch (NumberFormatException e) {
                throw new AcademicSystemException("Código de turma inválido: deve ser um número inteiro.");
            }

            System.out.print("Título da turma: ");
            String classroomName = input.nextLine();

            // Como ainda não existe a funcionalidade de cadastrar professor
            // separadamente (case 1 do menu ainda não foi implementado),
            // pedimos os dados do professor aqui mesmo, na hora de criar a turma.
            // Isso é provisório: quando "Register New Teacher" existir, o ideal
            // é selecionar um professor já cadastrado em vez de criar um novo aqui.
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

            // Valida o objeto inteiro (turma + professor) usando as anotações
            // de Bean Validation declaradas nas classes de domínio.
            validarTurma(classroom);

            // Só chega aqui se passou na validação. Tenta registrar no
            // AcademicSystem; se já existir uma turma com o mesmo ID,
            // o próprio AcademicSystem lança AcademicSystemException.
            AcademicSystem.getInstance().registerClassroom(classroom);

            System.out.println("Turma registrada com sucesso: " + classroom.getClassroomID());

        } catch (AcademicSystemException e) {
            // Captura qualquer erro de domínio (validação, ID duplicado,
            // código não numérico) e mostra mensagem amigável,
            // sem deixar o programa quebrar (mantém o menu rodando).
            System.out.println("Erro ao registrar turma: " + e.getMessage());
        }
    }

    /**
     * Roda a validação do Jakarta Bean Validation sobre o objeto Classroom
     * (que por sua vez também valida o Teacher dentro dele, se configurado
     * com @Valid — vale conferir se isso está anotado no domínio).
     *
     * Se houver qualquer violação (ex: campo em branco), monta uma mensagem
     * juntando todos os erros encontrados e lança AcademicSystemException,
     * conforme exige o AC3/AC6 da US-2363.
     */
    private void validarTurma(Classroom classroom) {
        Set<ConstraintViolation<Classroom>> violations = validator.validate(classroom);

        if (!violations.isEmpty()) {
            StringBuilder message = new StringBuilder("Dados de turma inválidos: ");
            for (ConstraintViolation<Classroom> violation : violations) {
                message.append(violation.getPropertyPath())
                        .append(" ")
                        .append(violation.getMessage())
                        .append("; ");
            }
            throw new AcademicSystemException(message.toString());
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

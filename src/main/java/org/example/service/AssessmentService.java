package org.example.service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.example.domain.Assessment;
import org.example.domain.Assignment;
import org.example.domain.Classroom;
import org.example.domain.ExamAssessment;
import org.example.domain.PracticalTask;
import org.example.domain.Seminar;
import org.example.exception.AcademicSystemException;
import org.example.sistema.AcademicSystem;

import java.util.Set;

/**
 * TUS-2397 - Lógica de negócio relacionada a avaliações.
 *
 * Responsável por (AC3):
 *  - encontrar a turma alvo,
 *  - criar o tipo de avaliação correto,
 *  - validar a avaliação,
 *  - e adicionar a avaliação à turma selecionada.
 *
 * Não sabe nada de teclado nem de menu (AC8): recebe os dados já
 * prontos de quem chama (menu, controller, futura tela JavaFX).
 */
public class AssessmentService {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    /**
     * Registra uma avaliação numa turma existente.
     *
     * @param classroomID código da turma onde a avaliação será adicionada
     * @param tipo        número do tipo (1=Exame, 2=Tarefa Prática,
     *                    3=Seminário, 4=Atribuição)
     * @param value       valor da avaliação
     * @param weight      peso da avaliação
     * @throws AcademicSystemException se a turma não existir (AC7),
     *         o tipo for inválido (AC6) ou os dados forem inválidos
     */
    public void registerAssessment(Long classroomID, int tipo, double value, double weight) {
        // AC7: procura a turma. Se não existir, não adiciona nada.
        Classroom classroom = AcademicSystem.getInstance()
                .findClassroomById(classroomID)
                .orElseThrow(() -> new AcademicSystemException(
                        "Turma não encontrada para o código: " + classroomID));

        // AC6: cria o tipo correto; tipo inválido lança exceção.
        Assessment assessment = createAssessment(tipo, value, weight);

        // valida valor e peso.
        validate(assessment);

        // adiciona à turma.
        classroom.adicionaAvaliacao(assessment);
    }

    /**
     * Cria a avaliação do tipo escolhido. Tipo fora de 1..4 lança
     * AcademicSystemException (AC6), então nenhuma avaliação é criada.
     */
    private Assessment createAssessment(int tipo, double value, double weight) {
        return switch (tipo) {
            case 1 -> new ExamAssessment(value, weight);
            case 2 -> new PracticalTask(value, weight);
            case 3 -> new Seminar(value, weight);
            case 4 -> new Assignment(value, weight);
            default -> throw new AcademicSystemException(
                    "Tipo de avaliação inválido: " + tipo);
        };
    }

    /**
     * Valida a avaliação via Bean Validation (valor e peso positivos).
     * Junta as violações e lança AcademicSystemException.
     */
    private void validate(Assessment assessment) {
        Set<ConstraintViolation<Assessment>> violations = validator.validate(assessment);

        if (!violations.isEmpty()) {
            StringBuilder message = new StringBuilder("Dados de avaliação inválidos: ");
            for (ConstraintViolation<Assessment> violation : violations) {
                message.append(violation.getPropertyPath())
                        .append(" ").append(violation.getMessage()).append("; ");
            }
            throw new AcademicSystemException(message.toString());
        }
    }
}
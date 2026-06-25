package org.example.service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.example.domain.Assessment;
import org.example.domain.Assignment;
import org.example.domain.Classroom;
import org.example.domain.Exam;
import org.example.domain.PracticalAssignment;
import org.example.domain.Seminar;
import org.example.exception.InvalidAssessmentException;
import org.example.sistema.AcademicSystem;

import java.util.Set;

/**
 * Responsável pela lógica de negócio relacionada a AVALIAÇÕES (Assessment).
 *
 * TUS-2397: essa lógica foi colocada aqui (e não dentro do menu) seguindo o
 * mesmo padrão do ClassService. O menu cuida só da entrada/saída (teclado),
 * e este service cuida da regra de negócio: achar a turma, criar o tipo
 * certo de avaliação, validar e adicionar a avaliação na turma. Assim dá
 * pra reaproveitar essa lógica em outras interfaces no futuro (ex: JavaFX).
 *
 * TUS-2397 - AC3: este service é responsável por:
 *   - encontrar a turma alvo,
 *   - criar o tipo correto de avaliação,
 *   - validar a avaliação,
 *   - e adicionar a avaliação à turma selecionada.
 * AC8: ele NÃO conhece teclado nem menu — só recebe dados prontos.
 */
public class AssessmentService {

    // Mesmo Validator usado pelo ClassService: reaproveita as anotações
    // (@NotBlank, @NotNull, @Positive...) declaradas na classe Assessment.
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    /**
     * Registra uma avaliação em uma turma existente.
     *
     * Quem chama (o menu, por exemplo) passa só dados simples: o código da
     * turma, o tipo de avaliação como texto ("Exam", "Seminar", ...) e os
     * valores. Este método se vira com o resto.
     *
     * Passo a passo (na ordem dos critérios da US-2361 / TUS-2397):
     *   1. acha a turma pelo código (AC1/AC4 da US-2361);
     *   2. cria o objeto do tipo certo de avaliação (AC2 da US-2361);
     *   3. valida os dados da avaliação (AC6 da US-2361);
     *   4. adiciona a avaliação na turma (AC1/AC7 da US-2361).
     *
     * @param classroomID    código da turma onde a avaliação será registrada
     * @param type           tipo da avaliação como texto: "Exam",
     *                       "PracticalAssignment", "Seminar" ou "Assignment"
     * @param assessmentID   identidade da avaliação
     * @param title          título da avaliação (ex: "Prova 1")
     * @param value          valor/nota da avaliação
     * @param weight         peso da avaliação na composição da turma
     * @throws InvalidAssessmentException se a turma não existir, se o tipo
     *         for desconhecido ou se os dados forem inválidos
     */
    public void registerAssessment(Long classroomID,
                                   String type,
                                   Long assessmentID,
                                   String title,
                                   double value,
                                   double weight) {

        // 1. Encontra a turma. Se não existir, não dá pra registrar nada.
        //    (US-2361 AC4: código de turma inexistente -> não registra.)
        Classroom classroom = AcademicSystem.getInstance()
                .findClassroomById(classroomID)
                .orElseThrow(() -> new InvalidAssessmentException(
                        "Não existe turma com o código: " + classroomID));

        // 2. Cria o objeto do tipo certo. Se o tipo for desconhecido,
        //    createAssessment lança exceção e nada é registrado.
        //    (US-2361 AC5: tipo inválido -> não registra.)
        Assessment assessment = createAssessment(type, assessmentID, title, value, weight);

        // 3. Valida os dados (título em branco, peso <= 0, etc).
        //    (US-2361 AC6: dados inválidos -> InvalidAssessmentException.)
        validate(assessment);

        // 4. Tudo certo: adiciona a avaliação na turma.
        //    (US-2361 AC1 e AC7.)
        classroom.addAssessment(assessment);
    }

    /**
     * "Fábrica" de avaliações: recebe o tipo como texto e devolve o objeto
     * concreto correspondente. É aqui que o polimorfismo entra — o método
     * devolve um Assessment, mas por baixo pode ser um Exam, um Seminar, etc.
     *
     * Usamos o nome simples da classe como chave (ignorando maiúsculas), o
     * mesmo texto que Assessment.getType() devolve. Assim "exam", "Exam" e
     * "EXAM" funcionam igual.
     *
     * @throws InvalidAssessmentException se o tipo não for reconhecido
     *         (US-2361 AC5).
     */
    private Assessment createAssessment(String type,
                                        Long assessmentID,
                                        String title,
                                        double value,
                                        double weight) {

        if (type == null) {
            throw new InvalidAssessmentException("Tipo de avaliação não informado.");
        }

        String normalized = type.trim().toLowerCase();

        switch (normalized) {
            case "exam":
                // Exam tem o construtor antigo (id, título, peso); o value
                // é ajustado em seguida para manter o mesmo dado das demais.
                Exam exam = new Exam(assessmentID, title, weight);
                exam.setValue(value);
                return exam;
            case "practicalassignment":
                return new PracticalAssignment(assessmentID, title, value, weight);
            case "seminar":
                return new Seminar(assessmentID, title, value, weight);
            case "assignment":
                return new Assignment(assessmentID, title, value, weight);
            default:
                throw new InvalidAssessmentException(
                        "Tipo de avaliação inválido: '" + type + "'. "
                                + "Tipos aceitos: Exam, PracticalAssignment, Seminar, Assignment.");
        }
    }

    /**
     * Roda a validação do Jakarta Bean Validation sobre a avaliação.
     * Se houver qualquer violação (ex: título em branco, peso <= 0),
     * junta todas as mensagens e lança InvalidAssessmentException.
     *
     * É exatamente o mesmo padrão do método validate() do ClassService —
     * só muda o tipo do objeto e a exceção lançada.
     */
    private void validate(Assessment assessment) {
        Set<ConstraintViolation<Assessment>> violations = validator.validate(assessment);

        if (!violations.isEmpty()) {
            StringBuilder message = new StringBuilder("Dados de avaliação inválidos: ");
            for (ConstraintViolation<Assessment> violation : violations) {
                message.append(violation.getPropertyPath())
                        .append(" ")
                        .append(violation.getMessage())
                        .append("; ");
            }
            throw new InvalidAssessmentException(message.toString());
        }
    }
}



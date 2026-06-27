package org.example.service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.example.domain.Classroom;
import org.example.exception.AcademicSystemException;
import org.example.sistema.AcademicSystem;

import java.util.Set;

/**
 * Responsável pela lógica de negócio relacionada a turmas (Classroom).
 *
 * TUS-2396: essa lógica antes vivia direto dentro do AdministratorMenu.
 * Foi movida para cá para que o menu fique só com a parte de
 * entrada/saída (teclado), e essa classe cuide só da regra de negócio
 * (validar e registrar a turma). Isso facilita reaproveitar essa lógica
 * em outras interfaces no futuro (ex: tela JavaFX).
 */
public class ClassService {

    // Validador do Jakarta Bean Validation, reaproveitando as anotações
    // (@NotBlank, @NotNull) que já existem nas classes de domínio.
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    /**
     * Valida e registra uma turma no AcademicSystem.
     *
     * Quem chama esse método já deve ter montado o objeto Classroom
     * com os dados coletados (do teclado, de um formulário, etc) —
     * esse método não sabe de onde os dados vieram, só sabe validar
     * e registrar.
     *
     * @param classroom turma já montada, ainda não validada
     * @throws AcademicSystemException se os dados forem inválidos
     *         (ex: campo em branco) ou se já existir uma turma com
     *         o mesmo código
     */
    public void registerClassroom(Classroom classroom) {
        validate(classroom);

        // Se passou na validação, tenta registrar. Se já existir uma
        // turma com o mesmo ID, o próprio AcademicSystem lança a
        // AcademicSystemException (turma duplicada).
        AcademicSystem.getInstance().registerClassroom(classroom);
    }

    /**
     * Roda a validação do Jakarta Bean Validation sobre o objeto Classroom.
     * Se houver qualquer violação (ex: campo em branco), monta uma
     * mensagem juntando todos os erros encontrados e lança
     * AcademicSystemException.
     */
    private void validate(Classroom classroom) {
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
}
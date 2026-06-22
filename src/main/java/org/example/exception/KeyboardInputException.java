package org.example.exception;

/**
 * Exceção lançada quando o usuário digita algo inválido no teclado
 * (ex: texto onde se esperava um número, opção de menu que não existe).
 *
 * Importante: essa exceção NÃO deve ser usada para erros de domínio
 * acadêmico (isso é AcademicSystemException) nem para erros de
 * autenticação/autorização (isso é AuthException). Ela serve só
 * para problemas na hora de LER a entrada do usuário.
 *
 * US-2368 - AC3: deve existir uma superclasse comum para erros de teclado.
 * AC5: deve ficar separada das exceções de domínio e de segurança.
 */
public class KeyboardInputException extends RuntimeException {

    public KeyboardInputException(String message) {
        super(message);
    }

    public KeyboardInputException(String message, Throwable cause) {
        super(message, cause);
    }
}

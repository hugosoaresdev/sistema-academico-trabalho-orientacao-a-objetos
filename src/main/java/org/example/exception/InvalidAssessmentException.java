package org.example.exception;

public class InvalidAssessmentException extends AcademicSystemException {

    public InvalidAssessmentException(String message) {
        super(message);
    }

    public InvalidAssessmentException(String message, Throwable cause) {
        super(message, cause);
    }
}
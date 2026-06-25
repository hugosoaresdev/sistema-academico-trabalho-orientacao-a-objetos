package org.example.exception;

public class InvalidClassroomException extends AcademicSystemException {

    public InvalidClassroomException(String message) {
        super(message);
    }

    public InvalidClassroomException(String message, Throwable cause) {
        super(message, cause);
    }
}
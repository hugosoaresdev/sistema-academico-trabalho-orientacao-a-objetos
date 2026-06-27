package org.example.security;

public abstract class AcademicSecurityException extends RuntimeException {
    public AcademicSecurityException(String message) {
        super(message);
    }
}
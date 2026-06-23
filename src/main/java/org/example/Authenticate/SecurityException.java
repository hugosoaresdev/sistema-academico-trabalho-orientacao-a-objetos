package org.example.Authenticate;

public abstract class SecurityException extends RuntimeException {
    public SecurityException(String message) {
        super(message);
    }
}
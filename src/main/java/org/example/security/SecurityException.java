package org.example.security;

public abstract class SecurityException extends RuntimeException {
    public SecurityException(String message) {
        super(message);
    }
}
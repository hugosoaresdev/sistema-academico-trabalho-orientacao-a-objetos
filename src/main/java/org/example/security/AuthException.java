package org.example.security;

public class AuthException extends RuntimeException {
    public AuthException(String message) {
        super(message);
    }
}

package ru.itmo.se.is.exception;

public class DeletionConflictException extends RuntimeException {
    public DeletionConflictException(String message) {
        super(message);
    }
}

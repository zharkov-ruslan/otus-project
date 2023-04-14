package ru.otus.demo.exception;

import lombok.NonNull;

public class BadRequestException extends RuntimeException {

    public BadRequestException(@NonNull final String message, @NonNull final Throwable throwable) {
        super(message, throwable);
    }
}
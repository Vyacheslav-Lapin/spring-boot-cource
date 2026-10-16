package ru.ibs.cources.java.spring.boot.cource.exceptions;

import lombok.Getter;

@Getter
public class ClientNotFoundException extends RuntimeException {
    private final String message;

    public ClientNotFoundException(String name) {
        this.message = "Client %s not found.".formatted(name);
    }
}

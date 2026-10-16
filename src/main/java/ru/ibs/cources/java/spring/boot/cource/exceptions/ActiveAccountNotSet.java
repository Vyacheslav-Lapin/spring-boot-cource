package ru.ibs.cources.java.spring.boot.cource.exceptions;

import lombok.Getter;

@Getter
public class ActiveAccountNotSet extends RuntimeException {

    private final String message;

    public ActiveAccountNotSet(String clientName) {
        message = "Active account not set for " + clientName;
    }
}

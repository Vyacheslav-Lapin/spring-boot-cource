package ru.ibs.cources.java.spring.boot.cource.exceptions;

import lombok.experimental.StandardException;

import java.io.Serial;

@StandardException
public class BankException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

}

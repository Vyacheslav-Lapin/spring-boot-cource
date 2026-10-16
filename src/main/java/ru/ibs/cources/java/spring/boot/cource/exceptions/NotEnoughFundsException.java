package ru.ibs.cources.java.spring.boot.cource.exceptions;

import lombok.Getter;

import java.io.Serial;

@Getter
public class NotEnoughFundsException extends BankException {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String message;

    public NotEnoughFundsException(double amount) {
        message = "Not Enough Funds " + amount;
    }
}

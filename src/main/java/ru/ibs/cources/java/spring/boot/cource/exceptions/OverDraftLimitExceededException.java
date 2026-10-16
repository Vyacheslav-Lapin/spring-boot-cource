package ru.ibs.cources.java.spring.boot.cource.exceptions;

import lombok.Getter;

import java.io.Serial;

@Getter
public class OverDraftLimitExceededException extends NotEnoughFundsException {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String message;

    public OverDraftLimitExceededException(String account, double amount) {
        super(amount);
        message = "Overdraft Limit exceeded on %s amount: %s".formatted(account, super.getMessage());
    }
}

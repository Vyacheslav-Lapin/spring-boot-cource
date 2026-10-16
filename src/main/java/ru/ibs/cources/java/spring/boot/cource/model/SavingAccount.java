package ru.ibs.cources.java.spring.boot.cource.model;

import ru.ibs.cources.java.spring.boot.cource.exceptions.NotEnoughFundsException;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public final class SavingAccount extends AbstractAccount {

    public SavingAccount(double initialBalance) {
        if (initialBalance >= 0) {
            setBalance(initialBalance);
        }
    }

    @Override
    public void withdraw(double amount) {
        if (getBalance() < amount) {
            throw new NotEnoughFundsException(amount);
        }

        setBalance(getBalance() - amount);
    }
}

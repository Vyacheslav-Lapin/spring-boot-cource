package ru.ibs.cources.java.spring.boot.cource.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

import static lombok.AccessLevel.*;

@Getter
@Setter(PACKAGE)
@NoArgsConstructor
@EqualsAndHashCode
public sealed abstract class AbstractAccount permits CheckingAccount, SavingAccount {

    @Setter(PUBLIC)
    private UUID id;

    private double balance;

    public void deposit(double amount) {
        if (amount < 0) {
            return;
        }

        balance += amount;
    }

    public abstract void withdraw(double amount);

    @Override
    public String toString() {
      return "\n\n\tbalance = %s".formatted(balance);
    }
}

package ru.ibs.cources.java.spring.boot.cource.model;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@NoArgsConstructor
@EqualsAndHashCode
@Setter(AccessLevel.PACKAGE)
public sealed abstract class AbstractAccount permits CheckingAccount, SavingAccount {

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

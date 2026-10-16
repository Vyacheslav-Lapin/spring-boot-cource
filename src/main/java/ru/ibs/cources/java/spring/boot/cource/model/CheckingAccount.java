package ru.ibs.cources.java.spring.boot.cource.model;

import ru.ibs.cources.java.spring.boot.cource.exceptions.OverDraftLimitExceededException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public final class CheckingAccount extends AbstractAccount {

    private double overdraft = 0;

    public CheckingAccount(double overdraft) {
        setOverdraft(overdraft);
    }

    public void setOverdraft(double amount) {
        if (overdraft < 0) {
            return;
        }

        overdraft = amount;
    }

    @Override
    public void withdraw(double amount) throws OverDraftLimitExceededException {
        if (getBalance() + overdraft < amount) {
            throw new OverDraftLimitExceededException(
                this.getClass().getSimpleName(), getBalance() + overdraft);
        }

        setBalance(getBalance() - amount);
    }
}


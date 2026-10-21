package ru.ibs.cources.java.spring.boot.cource.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.ExtensionMethod;
import ru.ibs.cources.java.spring.boot.cource.exceptions.OverDraftLimitExceededException;
import ru.ibs.cources.java.spring.boot.cource.utils.HibernateUtils;

import java.util.Objects;

import static ru.ibs.cources.java.spring.boot.cource.utils.HibernateUtils.*;

@Getter
@Entity
@NoArgsConstructor
@ExtensionMethod(value = HibernateUtils.class, suppressBaseMethods = false)
public class CheckingAccount extends AbstractAccount {

    private double overdraft;

    public CheckingAccount(double overdraft) {
//        setOverdraft(overdraft);
        if (overdraft < 0)
            return;

        this.overdraft = overdraft;
    }

    public void setOverdraft(double amount) {
        if (overdraft < 0)
            return;

        overdraft = amount;
    }

    @Override
    public void withdraw(double amount) throws OverDraftLimitExceededException {
        if (getBalance() + overdraft < amount) {
            throw new OverDraftLimitExceededException(
                getClass().getSimpleName(), getBalance() + overdraft);
        }

        setBalance(getBalance() - amount);
    }



    //region equals and hashCode
    @Override
    public boolean equals(Object object) {
        return this == object || object instanceof CheckingAccount checkingAccount
                                 && persistentClass(this) == checkingAccount.persistentClass()
                                 && Objects.equals(getId(), checkingAccount.getId());
    }

    @Override
    public int hashCode() {
        return persistentClass(this).hashCode();
    }
    //endregion
}


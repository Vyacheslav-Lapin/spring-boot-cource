package ru.ibs.cources.java.spring.boot.cource.model;

import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;
import lombok.experimental.ExtensionMethod;
import ru.ibs.cources.java.spring.boot.cource.exceptions.NotEnoughFundsException;
import ru.ibs.cources.java.spring.boot.cource.utils.HibernateUtils;

import java.util.Objects;

import static ru.ibs.cources.java.spring.boot.cource.utils.HibernateUtils.*;

@Entity
@NoArgsConstructor
@ExtensionMethod(value = HibernateUtils.class, suppressBaseMethods = false)
public class SavingAccount extends AbstractAccount {

    public SavingAccount(double initialBalance) {
        if (initialBalance >= 0)
            setBalance(initialBalance);
    }

    @Override
    public void withdraw(double amount) {
        if (getBalance() < amount)
            throw new NotEnoughFundsException(amount);
        setBalance(getBalance() - amount);
    }

    //region equals and hashCode
    @Override
    public boolean equals(Object object) {
        return this == object || object instanceof SavingAccount savingAccount
                                 && persistentClass(this) == savingAccount.persistentClass()
                                 && Objects.equals(getId(), savingAccount.getId());
    }

    @Override
    public int hashCode() {
        return persistentClass(this).hashCode();
    }
    //endregion
}

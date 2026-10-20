package ru.ibs.cources.java.spring.boot.cource.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.ExtensionMethod;
import ru.ibs.cources.java.spring.boot.cource.utils.HibernateUtils;

import java.util.UUID;

import static lombok.AccessLevel.*;

@Getter
@Entity
@Setter(PACKAGE)
@NoArgsConstructor
@ExtensionMethod(value = HibernateUtils.class, suppressBaseMethods =false)
@Table(name = "ACCOUNT")
public sealed abstract class AbstractAccount permits CheckingAccount, SavingAccount {

    @Id
    @Setter(PUBLIC)
    @GeneratedValue(strategy = GenerationType.UUID)
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

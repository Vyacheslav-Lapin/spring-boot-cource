package ru.ibs.cources.java.spring.boot.cource.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
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
@Table(name = "ACCOUNT")
@ExtensionMethod(value = HibernateUtils.class, suppressBaseMethods = false)
public abstract class AbstractAccount {

    @Id
    @GeneratedValue
    @Column(updatable = false, nullable = false)
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

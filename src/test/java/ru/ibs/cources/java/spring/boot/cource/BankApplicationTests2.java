package ru.ibs.cources.java.spring.boot.cource;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.ibs.cources.java.spring.boot.cource.exceptions.NotEnoughFundsException;
import ru.ibs.cources.java.spring.boot.cource.model.AbstractAccount;
import ru.ibs.cources.java.spring.boot.cource.model.CheckingAccount;
import ru.ibs.cources.java.spring.boot.cource.model.Client;
import ru.ibs.cources.java.spring.boot.cource.service.Banking;
import ru.ibs.cources.java.spring.boot.cource.service.ClientService;
import ru.ibs.cources.java.spring.boot.cource.service.operations.BankingOperationsService;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = BankApplication.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class BankApplicationTests2 {
    private final Banking banking;
    private final ClientService clientService;
    private final BankingOperationsService bankingOperationsService;

    private Client client;

    @BeforeEach
    public void init() {
        client = banking.getClient("Jonny Bravo");
        clientService.setDefaultActiveAccountIfNotSet(client);
        client.getActiveAccount().setId(UUID.randomUUID());
    }

    @Test
    public void depositToClient1() {
        AbstractAccount account = client.getActiveAccount();
        double balance = account.getBalance();
        double amount = 100;

        bankingOperationsService.deposit(client, amount);

        assertEquals(account.getBalance(), balance + amount);
    }

    @Test
    public void depositToClient2() {
        AbstractAccount account = client.getActiveAccount();
        double balance = account.getBalance();
        double amount = 100;

        bankingOperationsService.deposit(account, amount);

        assertEquals(account.getBalance(), balance + amount);
    }

    @Test
    public void getClientBalance() {
        AbstractAccount account = client.getActiveAccount();

        double balance = bankingOperationsService.getBalance(client);

        assertEquals(balance, account.getBalance());
    }

    @Test
    public void withdrawFromClient1() {
        AbstractAccount account = client.getActiveAccount();
        double balance = account.getBalance();
        double amount = 100;


        bankingOperationsService.withdraw(client, amount);

        assertEquals(account.getBalance(), balance - amount);
    }

    @Test
    public void withdrawFromClient2() {
        AbstractAccount account = client.getActiveAccount();
        double balance = account.getBalance();
        double amount = 100;


        bankingOperationsService.withdraw(account, amount);

        assertEquals(account.getBalance(), balance - amount);
    }

    @Test
    public void withdrawFromClient3() {
        AbstractAccount account = client.getActiveAccount();
        double balance = account.getBalance();
        double overdraft = 0;

        if (account instanceof CheckingAccount) {
            overdraft = ((CheckingAccount) account).getOverdraft();
        }

        double amount = balance + overdraft + 1000;

        assertThrows(NotEnoughFundsException.class,
                     () -> bankingOperationsService.withdraw(account, amount));

        assertEquals(account.getBalance(), balance);
    }

}

package com.luxoft.bankapp;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.ibs.cources.java.spring.boot.cource.BankApplication;
import ru.ibs.cources.java.spring.boot.cource.model.AbstractAccount;
import ru.ibs.cources.java.spring.boot.cource.model.Client;
import ru.ibs.cources.java.spring.boot.cource.model.SavingAccount;
import ru.ibs.cources.java.spring.boot.cource.service.Banking;
import ru.ibs.cources.java.spring.boot.cource.service.ClientService;
import ru.ibs.cources.java.spring.boot.cource.service.operations.BankingOperationsService;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("NewClassNamingConvention")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = BankApplication.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class BankApplicationTests4 {

  private static final String[] CLIENT_NAMES =
      {"Jonny Bravo", "Adam Budzinski", "Anna Smith"};

  private final Banking banking;
  private final BankingOperationsService bankingOperationsService;
  private final ClientService clientService;

  @Test
  public void deposit() {
    Client client = banking.getClient(CLIENT_NAMES[0]);

    assertEquals(CLIENT_NAMES[0], client.getName());

    double amount = 100;
    double expected = client.getActiveAccount().getBalance() + amount;

    clientService.deposit(client, 100);
    client = banking.getClient(CLIENT_NAMES[0]);

    assertEquals(expected, client.getActiveAccount().getBalance());
  }

  @Test
  public void withdraw() {
    Client client = banking.getClient(CLIENT_NAMES[0]);

    assertEquals(CLIENT_NAMES[0], client.getName());

    double amount = 100;
    double expected = client.getActiveAccount().getBalance() - amount;

    clientService.withdraw(client, 100);
    client = banking.getClient(CLIENT_NAMES[0]);

    assertEquals(expected, client.getActiveAccount().getBalance());
  }

  @Test
  public void adminResetBalance() throws Exception {
    Client client = banking.getClient(CLIENT_NAMES[0]);
    AbstractAccount account = clientService.getAccount(client, SavingAccount.class);

    bankingOperationsService.adminResetBalance(account);

    TimeUnit.SECONDS.sleep(1);

    client = banking.getClient(CLIENT_NAMES[0]);
    assertEquals(0, clientService.getAccount(client, SavingAccount.class).getBalance());
  }
}

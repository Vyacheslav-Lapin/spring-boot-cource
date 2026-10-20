package ru.ibs.cources.java.spring.boot.cource;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.ibs.cources.java.spring.boot.cource.audit.AuditService;
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
class BankApplicationTests3 {
  private final Banking banking;
  private final BankingOperationsService bankingOperationsService;
  private final ClientService clientService;
  private final AuditService auditService;

  private Client client;

  @BeforeEach
  public void init() {
    client = banking.getClient("Jonny Bravo");
    clientService.setDefaultActiveAccountIfNotSet(client);
    client.getActiveAccount().setId(UUID.randomUUID());
  }

  @Test
  public void depositToClient1() {
    double amount = 100;

    int countOfEvents =
        auditService.getEvents().size();

    bankingOperationsService.deposit(client, amount);

    assertEquals( countOfEvents + 1,
                  auditService.getEvents().size());
  }

  @Test
  public void depositToClient2() {
    AbstractAccount account = client.getActiveAccount();
    double amount = 100;
    int countOfEvents = auditService.getEvents().size();
    bankingOperationsService.deposit(account, amount);

    assertEquals(countOfEvents + 1,
                 auditService.getEvents().size());
  }

  @Test
  public void getClientBalance() {
    int countOfEvents = auditService.getEvents().size();
    bankingOperationsService.getBalance(client);

    assertEquals( countOfEvents + 1,
                  auditService.getEvents().size());
  }

  @Test
  public void withdrawFromClient1() {
    double amount = 100;
    int countOfEvents = auditService.getEvents().size();
    bankingOperationsService.withdraw(client, amount);

    assertEquals(countOfEvents + 2,
                 auditService.getEvents().size());
  }

  @Test
  public void withdrawFromClient2() {
    AbstractAccount account = client.getActiveAccount();
    double amount = 100;
    int countOfEvents = auditService.getEvents().size();
    bankingOperationsService
        .withdraw(account, amount);

    assertEquals( countOfEvents + 2,
                  auditService.getEvents().size());
  }

  @Test
  public void withdrawFromClient3() {
    AbstractAccount account = client.getActiveAccount();
    double balance = account.getBalance();
    double overdraft = 0;

    if (account instanceof CheckingAccount) {
      overdraft = ((CheckingAccount) account).getOverdraft();
    }

    double amount = balance + overdraft + 1_000;

    int countOfEvents = auditService.getEvents().size();
    assertThrows(NotEnoughFundsException.class,
                 () -> bankingOperationsService
                     .withdraw(account, amount));

    assertEquals(auditService.getEvents().size(),
                 countOfEvents + 2);
  }
}

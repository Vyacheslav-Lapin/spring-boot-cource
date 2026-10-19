package ru.ibs.cources.java.spring.boot.cource;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import ru.ibs.cources.java.spring.boot.cource.audit.AuditService;
import ru.ibs.cources.java.spring.boot.cource.model.AbstractAccount;
import ru.ibs.cources.java.spring.boot.cource.model.Client;
import ru.ibs.cources.java.spring.boot.cource.service.Banking;
import ru.ibs.cources.java.spring.boot.cource.service.ClientService;
import ru.ibs.cources.java.spring.boot.cource.service.operations.BankingOperationsService;

import java.util.UUID;

import static org.mockito.Mockito.*;


@SpringBootTest(classes = BankApplication.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class BankApplicationTests3 {

  private final Banking banking;
  private final BankingOperationsService bankingOperationsService;
  private final ClientService clientService;

  @MockitoSpyBean
  private AuditService auditService;

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

    bankingOperationsService.deposit(client, amount);

    verify(auditService, times(1))
        .auditDeposit(client.getActiveAccount().getId(), amount);
  }

  @Test
  public void depositToClient2() {
    AbstractAccount account = client.getActiveAccount();
    double amount = 100;

    bankingOperationsService.deposit(account, amount);

    verify(auditService, times(1))
        .auditDeposit(account.getId(), amount);
  }

//    @Test
//    public void getClientBalance() {
//        bankingOperationsService.getBalance(client);
//
//        verify(auditService, times(1))
//                .auditBalance(client.getActiveAccount().getId());
//
//    }
//
//    @Test
//    public void withdrawFromClient1() {
//        double amount = 100;
//
//        bankingOperationsService.withdraw(client, amount);
//
//        verify(auditService, times(1))
//                .auditWithdraw(client.getActiveAccount().getId(), amount, WithdrawState.TRYING);
//
//        verify(auditService, times(1))
//                .auditWithdraw(client.getActiveAccount().getId(), amount, WithdrawState.SUCCESSFUL);
//    }
//
//    @Test
//    public void withdrawFromClient2() {
//        AbstractAccount account = client.getActiveAccount();
//        double amount = 100;
//
//        bankingOperationsService.withdraw(account, amount);
//
//        verify(auditService, times(1))
//                .auditWithdraw(client.getActiveAccount().getId(), amount, WithdrawState.TRYING);
//
//        verify(auditService, times(1))
//                .auditWithdraw(client.getActiveAccount().getId(), amount, WithdrawState.SUCCESSFUL);
//    }
//
//    @Test
//    public void withdrawFromClient3() {
//        AbstractAccount account = client.getActiveAccount();
//        double balance = account.getBalance();
//        double overdraft = 0;
//
//        if (account instanceof CheckingAccount) {
//            overdraft = ((CheckingAccount) account).getOverdraft();
//        }
//
//        double amount = balance + overdraft + 1000;
//
//        assertThrows(NotEnoughFundsException.class,
//                () -> bankingOperationsService.withdraw(account, amount));
//
//        verify(auditService, times(1))
//                .auditWithdraw(client.getActiveAccount().getId(), amount, WithdrawState.TRYING);
//
//        verify(auditService, times(1))
//                .auditWithdraw(client.getActiveAccount().getId(), amount, WithdrawState.FAILED);
//    }

}

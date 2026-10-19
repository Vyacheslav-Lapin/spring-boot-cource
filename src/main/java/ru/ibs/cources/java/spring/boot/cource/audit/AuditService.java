package ru.ibs.cources.java.spring.boot.cource.audit;

import org.springframework.stereotype.Service;

import java.util.UUID;

public sealed interface AuditService {

  void auditDeposit(UUID accountId, double amount);
  void auditWithdraw(long accountId, double amount, WithdrawState state);
  void auditBalance(long accountId);
}

@Service
final class AuditServiceImpl implements AuditService {


  @Override
  public void auditDeposit(UUID accountId, double amount) {
    System.out.println("ACCOUNT ID: " + accountId + " DEPOSIT: " + amount);
  }

  @Override
  public void auditWithdraw(long accountId, double amount, WithdrawState state) {
    System.out.println("ACCOUNT ID: " + accountId + " " + state + " WITHDRAWAL: " + amount);
  }

  @Override
  public void auditBalance(long accountId) {
    System.out.println("ACCOUNT ID: " + accountId + " BALANCE CHECK");
  }

}

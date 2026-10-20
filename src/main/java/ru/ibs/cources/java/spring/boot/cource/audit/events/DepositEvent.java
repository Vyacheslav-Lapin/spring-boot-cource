package ru.ibs.cources.java.spring.boot.cource.audit.events;

import lombok.Getter;

import java.util.UUID;

@Getter
public class DepositEvent extends AccountEvent {
  private final double amount;
  public DepositEvent(UUID accountId, double amount) {
    super("DEPOSIT", accountId);
    this.amount = amount;
  }
}

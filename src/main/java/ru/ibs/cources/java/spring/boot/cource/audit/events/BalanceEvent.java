package ru.ibs.cources.java.spring.boot.cource.audit.events;

import java.util.UUID;

public class BalanceEvent extends AccountEvent {
  public BalanceEvent(UUID accountId) {
    super("BALANCE CHECK", accountId);
  }
}

package ru.ibs.cources.java.spring.boot.cource.audit.events;

import lombok.Getter;

import java.util.UUID;

@Getter
public class WithdrawEvent extends AccountEvent {

  private final double amount;
  private final State state;

  public WithdrawEvent(UUID accountId, double amount, State state) {
    super("WITHDRAWAL", accountId);
    this.amount = amount;
    this.state = state;
  }

  public WithdrawEvent(UUID accountId, double amount) {
    this(accountId, amount, State.TRYING);
  }

  public enum State {
    TRYING, SUCCESSFUL, FAILED
  }
}

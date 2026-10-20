package ru.ibs.cources.java.spring.boot.cource.audit.events;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.UUID;

@Getter
public abstract class AccountEvent extends ApplicationEvent {

  private final UUID accountId;

  public AccountEvent(Object source, UUID accountId) {
    super(source);
    this.accountId = accountId;
  }
}


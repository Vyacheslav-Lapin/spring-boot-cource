package ru.ibs.cources.java.spring.boot.cource.audit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import ru.ibs.cources.java.spring.boot.cource.audit.events.AccountEvent;
import ru.ibs.cources.java.spring.boot.cource.audit.events.BalanceEvent;
import ru.ibs.cources.java.spring.boot.cource.audit.events.DepositEvent;
import ru.ibs.cources.java.spring.boot.cource.audit.events.WithdrawEvent;

import java.util.ArrayList;
import java.util.List;

public sealed interface AuditService {

  @EventListener(condition = "#event.amount >= @eventConditionFilter.depositLimit")
  void auditOperation(DepositEvent event);

  @EventListener(condition = "#event.amount >= @eventConditionFilter.withdrawalLimit")
  void auditOperation(WithdrawEvent event);

  @EventListener void auditOperation(BalanceEvent event);

  List<AccountEvent> getEvents();
}

@Slf4j
@Service
final class AuditServiceImpl implements AuditService {

  private final List<AccountEvent> events = new ArrayList<>(100);

  @Override
  public void auditOperation(DepositEvent event) {
    events.add(event);
    log.info("ACCOUNT ID: {} {}: {}", event.getAccountId(), event.getSource(), event.getAmount());
  }

  @Override
  public void auditOperation(WithdrawEvent event) {
    events.add(event);
    log.info("ACCOUNT ID: {} {} {}: {}", event.getAccountId(), event.getState(), event.getSource(), event.getAmount());
  }

  @Override
  public void auditOperation(BalanceEvent event) {
    events.add(event);
    log.info("ACCOUNT ID: {} {}", event.getAccountId(), event.getSource());
  }

  @Override
  public List<AccountEvent> getEvents() {
    return new ArrayList<>(events);
  }
}

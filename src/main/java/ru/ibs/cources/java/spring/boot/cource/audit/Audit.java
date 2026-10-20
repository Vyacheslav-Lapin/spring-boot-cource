package ru.ibs.cources.java.spring.boot.cource.audit;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Configuration;
import ru.ibs.cources.java.spring.boot.cource.audit.events.BalanceEvent;
import ru.ibs.cources.java.spring.boot.cource.audit.events.DepositEvent;
import ru.ibs.cources.java.spring.boot.cource.audit.events.WithdrawEvent;
import ru.ibs.cources.java.spring.boot.cource.model.AbstractAccount;
import ru.ibs.cources.java.spring.boot.cource.model.Client;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.UUID;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

@Retention(RUNTIME)
@Target({ANNOTATION_TYPE})
public @interface Audit {
}

@Aspect
@Configuration
@RequiredArgsConstructor
class AuditAspect {

  private final ApplicationEventPublisher eventPublisher;

  @Pointcut("execution(* ru.ibs.cources.java.spring.boot.cource.service.operations.*.deposit(..))")
  public void anyDeposit() {}

  @Pointcut("@annotation(Audit) || within(@Audit *)"
            + " || execution(@(@Audit *) * *(..)) || within (@(@Audit *) *)"
            + " || execution(@(@(@Audit *) *) * *(..)) || within (@(@(@Audit *) *) *)"
            + " || execution(@(@(@(@Audit *) *) *) * *(..)) || within (@(@(@(@Audit *) *) *) *)"
            + " || execution(@(@(@(@(@Audit *) *) *) *) * *(..)) || within (@(@(@(@(@Audit *) *) *) *) *)"
            + " || execution(@(@(@(@(@(@Audit *) *) *) *) *) * *(..)) || within (@(@(@(@(@(@Audit *) *) *) *) *) *)"
            + " || execution(@(@(@(@(@(@(@Audit *) *) *) *) *) *) * *(..)) || within (@(@(@(@(@(@(@Audit *) *) *) *) *) *) *)"
            + " || execution(@(@(@(@(@(@(@(@Audit *) *) *) *) *) *) *) * *(..)) || within (@(@(@(@(@(@(@(@Audit *) *) *) *) *) *) *) *)")
  void auditPointcut() {
  }

  @Pointcut("execution(* ru.ibs.cources.java.spring.boot.cource.service.operations.*.withdraw(..))")
  public void anyWithdraw() {}

  @Pointcut("execution(* ru.ibs.cources.java.spring.boot.cource.service.operations.*.getBalance(..))")
  public void anyBalance() {}

  private UUID getAccountId(Object[] methodArgs) {

    AbstractAccount account;

    if (methodArgs[0] instanceof Client client) {
      account = client.getActiveAccount();
    } else {
      account = (AbstractAccount) methodArgs[0];
    }

    return account.getId();
  }

  @Before("anyDeposit()")
  public void logDeposit(JoinPoint joinPoint) {
    Object[] methodArgs = joinPoint.getArgs();
    eventPublisher.publishEvent(new DepositEvent(getAccountId(methodArgs), (double) methodArgs[1]));
  }

  @Before("anyBalance()")
  public void logBalance(JoinPoint joinPoint) {
    val methodArgs = joinPoint.getArgs();
    eventPublisher.publishEvent(new BalanceEvent(getAccountId(methodArgs)));
  }

  @Around("anyWithdraw()")
  public Object logWithdrawal(ProceedingJoinPoint thisJoinPoint) throws Throwable {
    Object[] methodArgs = thisJoinPoint.getArgs();

    UUID accountId = getAccountId(methodArgs);
    eventPublisher.publishEvent(new WithdrawEvent(accountId, (double) methodArgs[1]));

    Object result;
    try {
      result = thisJoinPoint.proceed();
      eventPublisher.publishEvent(
          new WithdrawEvent(accountId,
                            (double) methodArgs[1],
                            WithdrawEvent.State.SUCCESSFUL));
      ;
    } catch (Exception e) {
      eventPublisher.publishEvent(
          new WithdrawEvent(accountId,
                            (double) methodArgs[1],
                            WithdrawEvent.State.FAILED));

      throw e;
    }

    return result;
  }
}

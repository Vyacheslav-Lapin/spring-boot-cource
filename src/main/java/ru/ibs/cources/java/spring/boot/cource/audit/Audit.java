package ru.ibs.cources.java.spring.boot.cource.audit;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
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

  private final AuditService audit;

  @Pointcut("execution(* ru.ibs.cources.java.spring.boot.cource.service.operations.*.deposit(..))")
  public void anyDeposit() {}

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
    audit.auditDeposit(getAccountId(methodArgs), (double) methodArgs[1]);
  }

}

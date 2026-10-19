package ru.ibs.cources.java.spring.boot.cource.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.experimental.ExtensionMethod;
import lombok.val;
import org.springframework.stereotype.Service;
import ru.ibs.cources.java.spring.boot.cource.exceptions.AccountNumberLimitException;
import ru.ibs.cources.java.spring.boot.cource.exceptions.ActiveAccountNotSet;
import ru.ibs.cources.java.spring.boot.cource.model.AbstractAccount;
import ru.ibs.cources.java.spring.boot.cource.model.CheckingAccount;
import ru.ibs.cources.java.spring.boot.cource.model.Client;
import ru.ibs.cources.java.spring.boot.cource.model.SavingAccount;
import ru.ibs.cources.java.spring.boot.cource.service.storage.ClientRepository;
import ru.ibs.cources.java.spring.boot.cource.service.storage.ClientRepositoryUtils;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static ru.ibs.cources.java.spring.boot.cource.BankApplication.*;

@Service
@ExtensionMethod(suppressBaseMethods = false, value = {
    ClientRepositoryUtils.class,
})
@RequiredArgsConstructor
public class ClientService {

  private final ClientRepository repository;

  @PostConstruct
  private void init() {
    val jonnyBravo = new Client(CLIENT_NAMES.getFirst(), Client.Gender.MALE);
    addAccount(jonnyBravo, new SavingAccount(1_000));
    addAccount(jonnyBravo, new CheckingAccount(1_000));

    val adamBudxinski = addAccount(
        new Client(CLIENT_NAMES.get(1), Client.Gender.MALE),
        new CheckingAccount(1_500));

    repository.addAll(jonnyBravo, adamBudxinski);
  }

  public synchronized double getBalance(Client client) {
    if (!checkIfActiveAccountSet(client)) {
      throw new ActiveAccountNotSet(client.getName());
    }

    return client.getActiveAccount().getBalance();
  }

  public synchronized void deposit(Client client, double amount) {
    if (!checkIfActiveAccountSet(client)) {
      throw new ActiveAccountNotSet(client.getName());
    }

    client.getActiveAccount().deposit(amount);
    repository.update(client);
  }

  public synchronized void withdraw(Client client, double amount) {
    if (!checkIfActiveAccountSet(client)) {
      throw new ActiveAccountNotSet(client.getName());
    }

    client.getActiveAccount().withdraw(amount);
    repository.update(client);
  }

  private boolean checkIfActiveAccountSet(Client client) {
    return client.getActiveAccount() != null;
  }

  public void removeAccount(Client client, Class<?> type) {
    client.setAccounts(client.getAccounts().stream()
                       .filter(a -> a.getClass() != type)
                       .collect(Collectors.toList()));

    repository.update(client);
  }

  public void setAccounts(Client client, Set<AbstractAccount> accounts) {
    client.getAccounts().clear();
    client.getAccounts().addAll(accounts);
    repository.update(client);
  }

  public List<AbstractAccount> getAccounts(Client client) {
    return Collections.unmodifiableList(client.getAccounts());
  }

  @SuppressWarnings("unchecked")
  public <T extends AbstractAccount> T getAccount(Client client, Class<T> type) {
    for (AbstractAccount account : client.getAccounts()) {
      if (type.isInstance(account)) {
        return (T) account;
      }
    }

    return null;
  }

  public Client addAccount(Client client, AbstractAccount account) throws AccountNumberLimitException {
    if (client.getAccounts().size() >= 2) {
      throw new AccountNumberLimitException();
    }

    if (account != null) {
      client.getAccounts().add(account);
    }

    return client;
  }

  public void setDefaultActiveAccountIfNotSet(Client client) {
    if (client.getActiveAccount() == null && client.getAccounts() != null && !client.getAccounts().isEmpty()) {
      AbstractAccount account = getAccount(client,CheckingAccount.class);
      if (account == null) {
        account = client.getAccounts().iterator().next();
      }

      client.setActiveAccount(account);
      repository.update(client);

      System.out.printf("Default account set for %s:%s%n",
                        client.getName(),
                        client.getActiveAccount().getClass().getSimpleName());
    }
  }

  private StringBuilder getSimpleInfoBuilder(Client client) {
    StringBuilder builder = new StringBuilder();

    builder.append("\nClient: ")
           .append(client.getName())
           .append("\nGender: ")
           .append(client.getGender());

    return builder;
  }

  public String toString(Client client) {

    StringBuilder builder = getSimpleInfoBuilder(client);

    builder.append("\nAccounts:");

    for (AbstractAccount account : client.getAccounts()) {
      builder.append(account.toString());
    }

    builder.append("\nActive account: ");

    builder.append(checkIfActiveAccountSet(client) ?
                   client.getActiveAccount().getClass().getSimpleName() : "not set");

    return builder.toString();
  }
}

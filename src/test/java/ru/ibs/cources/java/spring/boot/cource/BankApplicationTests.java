package ru.ibs.cources.java.spring.boot.cource;

import lombok.RequiredArgsConstructor;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.ibs.cources.java.spring.boot.cource.model.AbstractAccount;
import ru.ibs.cources.java.spring.boot.cource.model.CheckingAccount;
import ru.ibs.cources.java.spring.boot.cource.model.Client;
import ru.ibs.cources.java.spring.boot.cource.model.SavingAccount;
import ru.ibs.cources.java.spring.boot.cource.service.Banking;
import ru.ibs.cources.java.spring.boot.cource.service.ClientService;
import ru.ibs.cources.java.spring.boot.cource.service.storage.ClientRepository;
import ru.ibs.cources.java.spring.boot.cource.service.storage.MapClientRepository;

import static org.assertj.core.api.Assertions.*;
import static ru.ibs.cources.java.spring.boot.cource.BankApplication.*;

@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class BankApplicationTests {

  private final ClientRepository repository;
  private final Banking banking;
  public final ClientService clientService;

  @Test
  void contextLoads() {
  }

  @Test
  @SuppressWarnings("ResultOfMethodCallIgnored")
  //todo 16.10.2026: тест надо будет изменить или снести как только переедем на БД
  public void repositoryBeanConfiguration() {
    assertThat(repository)
        .isInstanceOf(MapClientRepository.class)
        .withFailMessage("repository should be instantiated with MapClientRepository class");
  }

  @Test
  public void initializationClient1() {
    // when
    Client client = banking.getClient(CLIENT_NAMES.getFirst());
    assertThat(client).isNotNull()
                      // then
                      .withFailMessage(() -> "banking should have client with name: %s".formatted(CLIENT_NAMES.getFirst()))
                      .extracting(Client::getAccounts, InstanceOfAssertFactories.list(AbstractAccount.class))
                      .hasSize(2);
  }

  @Test
  public void client1SavingAccount() {
    Client client = banking.getClient(CLIENT_NAMES.getFirst());
    SavingAccount account = clientService.getAccount(client, SavingAccount.class);

    // when
    assertThat(account).isNotNull()
                        // then
                       .withFailMessage(() -> "%s should have %s account".formatted(client.getName(), SavingAccount.class.getSimpleName()))
                       .extracting(AbstractAccount::getBalance)
                       .isEqualTo(1_000d);
  }

  @Test
  public void client1CheckingAccount() {
    Client client = banking.getClient(CLIENT_NAMES.getFirst());

    // when
    assertThat(clientService.getAccount(client, CheckingAccount.class)).isNotNull()
                       // then
                       .withFailMessage(() -> "%s should have %s account".formatted(client.getName(), CheckingAccount.class.getSimpleName()))
                       .satisfies(account -> assertThat(account.getBalance()).isEqualTo(0))
//                       .satisfies(account -> assertThat(account.getBalance()).isEqualTo(4_000))
                       .satisfies(account -> assertThat(account.getOverdraft()).isEqualTo(1_000));
  }
}

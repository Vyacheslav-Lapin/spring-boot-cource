package ru.ibs.cources.java.spring.boot.cource;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import ru.ibs.cources.java.spring.boot.cource.model.CheckingAccount;
import ru.ibs.cources.java.spring.boot.cource.model.Client;
import ru.ibs.cources.java.spring.boot.cource.model.SavingAccount;
import ru.ibs.cources.java.spring.boot.cource.service.ClientService;

import static ru.ibs.cources.java.spring.boot.cource.model.Client.Gender.*;


@Configuration
@Profile("dev")
@RequiredArgsConstructor
@PropertySource("classpath:clients.properties")
public class DevConfiguration {

  private final Environment environment;
  private final ApplicationContext applicationContext;

  @Bean
  Client client1(ClientService clientService) {
    String name = environment.getProperty("client1");

    Client client = new Client(name, MALE);
    client.setCity("Moscow");

    clientService.addAccount(client, applicationContext.getBean("savingAccount1", SavingAccount.class));
    clientService.addAccount(client, applicationContext.getBean("checkingAccount1", CheckingAccount.class));

    return client;
  }

  @Bean(name = "savingAccount1")
  public SavingAccount getDemoSavingAccount1() {
    return new SavingAccount(1000);
  }

  @Bean(name = "checkingAccount1")
  public CheckingAccount getDemoCheckingAccount1() {

    CheckingAccount checkingAccount = new CheckingAccount(1_000);
    checkingAccount.deposit(4_000);

    return checkingAccount;
  }

  @Bean(name = "checkingAccount2")
  public CheckingAccount getDemoCheckingAccount2() {
    CheckingAccount checkingAccount = new CheckingAccount(1_500);
    checkingAccount.withdraw(500);

    return checkingAccount;
  }

  @Bean(name = "client2")
  public Client getDemoClient2(ClientService clientService) {
    Client client = new Client(environment.getProperty("client2"), MALE);
    client.setCity("Kiev");
    clientService.addAccount(client, applicationContext.getBean("checkingAccount2", CheckingAccount.class));

    return client;
  }
}

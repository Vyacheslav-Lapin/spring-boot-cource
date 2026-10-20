package ru.ibs.cources.java.spring.boot.cource;

import lombok.Cleanup;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static io.vavr.API.*;

@SpringBootApplication
//@ComponentScan(includeFilters = @ComponentScan.Filter(@Aspect))
@RequiredArgsConstructor
public class BankApplication {

//  public static final String[] CLIENT_NAMES = {
//      "Jonny Bravo",
//      "Adam Budzinski",
//      "Anna Smith",
//  };

  @SuppressWarnings({"unchecked", "rawtypes"})
  public static final List<String> CLIENT_NAMES = CheckedFunction(() -> {
    val properties = new Properties(3);
    @Cleanup val clientsIoStream = BankApplication.class.getResourceAsStream("/clients.properties");
    properties.load(clientsIoStream);
    return (List<String>) new ArrayList(properties.values());
  }).unchecked().apply();

//  @Bean CommandLineRunner workWithExistingClients(ClientService clientService,
//                                                  Banking banking,
//                                                  BankReportService reportService) {
//    return _ -> {
//      System.out.println("\n=======================================");
//      System.out.println("\n===== Work with existing clients ======");
//
//      Client jonny = banking.getClient(CLIENT_NAMES.getFirst());
//
//      try {
//        clientService.deposit(jonny, 5_000);
//      } catch (ActiveAccountNotSet e) {
//        System.out.println(e.getMessage());
//        clientService.setDefaultActiveAccountIfNotSet(jonny);
//        clientService.deposit(jonny, 5_000);
//      }
//
//      System.out.println(clientService.toString(jonny));
//
//      Client adam = banking.getClient(CLIENT_NAMES.get(1));
//      clientService.setDefaultActiveAccountIfNotSet(adam);
//
//      clientService.withdraw(adam, 1_500);
//
//      double balance = clientService.getBalance(adam);
//      System.out.printf("\n%s, current balance: %s%n", adam.getName(), balance);
//
//      banking.transferMoney(jonny, adam, 1_000);
//
//      System.out.println("\n=======================================");
//      banking.getClients().forEach(System.out::println);
//
//      System.out.println("\n=== Initialization using Banking implementation ===\n");
//
//      Client anna = new Client(CLIENT_NAMES.get(2), Client.Gender.FEMALE);
//      anna = banking.addClient(anna);
//
//      AbstractAccount saving = banking.createAccount(anna, SavingAccount.class);
//      saving.deposit(1_000);
//
//      banking.updateAccount(anna, saving);
//
//      AbstractAccount checking = banking.createAccount(anna, CheckingAccount.class);
//      checking.deposit(3_000);
//
//      banking.updateAccount(anna, checking);
//
//      banking.getAllAccounts(anna)
//             .forEach(System.out::println);
//
//      System.out.println("\n=== Using BankReportService ===\n");
//      System.out.println("Number of clients: " + reportService.getNumberOfBankClients());
//      System.out.println("Number of accounts: " + reportService.getAccountsNumber());
//      System.out.println("Bank Credit Sum: " + reportService.getBankCreditSum());
//    };
//  }

  static void main(String[] args) {
    SpringApplication.run(BankApplication.class, args);
  }
}

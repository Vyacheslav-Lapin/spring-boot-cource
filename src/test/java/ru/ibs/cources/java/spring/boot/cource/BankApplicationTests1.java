package ru.ibs.cources.java.spring.boot.cource;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.ibs.cources.java.spring.boot.cource.model.AbstractAccount;
import ru.ibs.cources.java.spring.boot.cource.model.CheckingAccount;
import ru.ibs.cources.java.spring.boot.cource.model.Client;
import ru.ibs.cources.java.spring.boot.cource.model.SavingAccount;
import ru.ibs.cources.java.spring.boot.cource.service.BankReportService;
import ru.ibs.cources.java.spring.boot.cource.service.Banking;
import ru.ibs.cources.java.spring.boot.cource.service.ClientService;
import ru.ibs.cources.java.spring.boot.cource.service.demo.BankInitializationService;
import ru.ibs.cources.java.spring.boot.cource.service.storage.ClientRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = BankApplication.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class BankApplicationTests1 {
    private static final String[] CLIENT_NAMES = {"Jonny Bravo", "Adam Budzinski", "Anna Smith"};

    private final Banking banking;
    private final ClientRepository clientRepository;
    private final ClientService clientService;
    private final BankReportService bankReport;
    private final BankInitializationService initializationService;

    @Test
    public void clientRepositoryBeanConfiguration() {
        assertNotNull(clientRepository, "clientRepository bean should be configured");
        assertTrue((clientRepository instanceof ClientRepository), "clientRepository should be instantiated with ClientRepository class");
    }

    @Test
    public void initializationClient1() {
        Client client = banking.getClient(CLIENT_NAMES[0]);
        assertNotNull(client, "banking should have client with name: " + CLIENT_NAMES[0]);

        assertEquals(2, client.getAccounts().size());
    }

    @Test
    public void client1SavingAccount() {
        Client client = banking.getClient(CLIENT_NAMES[0]);

        AbstractAccount account = clientService.getAccount(client, SavingAccount.class);

        assertNotNull(account,
                client.getName() + "should have saving account");

        assertEquals(1000, account.getBalance());
    }

    @Test
    public void client1CheckingAccount() {
        Client client = banking.getClient(CLIENT_NAMES[0]);

        CheckingAccount account = clientService.getAccount(client, CheckingAccount.class);

        assertNotNull(account,
                client.getName() + "should have checking account");

        assertEquals(4000, account.getBalance());
        assertEquals(1000, account.getOverdraft());
    }

    @Test
    public void initializationClient2() {
        Client client = banking.getClient(CLIENT_NAMES[1]);
        assertNotNull(client, "banking should have client with name: " + CLIENT_NAMES[1]);

        assertEquals(1, client.getAccounts().size());
    }

    @Test
    public void client2CheckingAccount() {
        Client client = banking.getClient(CLIENT_NAMES[1]);

        CheckingAccount account = clientService.getAccount(client, CheckingAccount.class);

        assertNotNull(account,
                client.getName() + "should have checking account");

//        assertEquals(0, account.getBalance());
//        assertEquals(0, account.getBalance());
//        assertEquals(-500, account.getBalance());
        assertEquals(1500, account.getOverdraft());
    }
}

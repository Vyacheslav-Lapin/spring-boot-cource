package ru.ibs.cources.java.spring.boot.cource.service.operations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ibs.cources.java.spring.boot.cource.model.AbstractAccount;
import ru.ibs.cources.java.spring.boot.cource.model.Client;
import ru.ibs.cources.java.spring.boot.cource.service.ClientService;

public interface BankingOperationsService {

    double getBalance(Client client);

    double getBalance(AbstractAccount account);

    void deposit(Client client, double amount);

    void deposit(AbstractAccount account, double amount);

    void withdraw(Client client, double amount);

    void withdraw(AbstractAccount client, double amount);
}

@Service
@RequiredArgsConstructor
class BankingOperationsServiceImpl implements BankingOperationsService {

    private final ClientService clientService;

    @Override
    public double getBalance(Client client) {
        return clientService.getBalance(client);
    }

    @Override
    public double getBalance(AbstractAccount account) {
        return account.getBalance();
    }

    @Override
    public void deposit(Client client, double amount) {
        clientService.deposit(client, amount);
    }

    @Override
    public void deposit(AbstractAccount account, double amount) {
        account.deposit(amount);
    }

    @Override
    public void withdraw(Client client, double amount) {
        clientService.withdraw(client, amount);
    }

    @Override
    public void withdraw(AbstractAccount account, double amount) {
        account.withdraw(amount);
    }
}

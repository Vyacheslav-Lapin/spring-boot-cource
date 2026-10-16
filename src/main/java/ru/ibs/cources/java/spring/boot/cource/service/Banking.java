package ru.ibs.cources.java.spring.boot.cource.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ibs.cources.java.spring.boot.cource.exceptions.AccountNotFoundException;
import ru.ibs.cources.java.spring.boot.cource.exceptions.ClientNotFoundException;
import ru.ibs.cources.java.spring.boot.cource.model.AbstractAccount;
import ru.ibs.cources.java.spring.boot.cource.model.CheckingAccount;
import ru.ibs.cources.java.spring.boot.cource.model.Client;
import ru.ibs.cources.java.spring.boot.cource.model.SavingAccount;
import ru.ibs.cources.java.spring.boot.cource.service.storage.ClientRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public sealed interface Banking {

    Client addClient(Client client);

    Optional<Client> getClientOpt(String name);

    default Client getClient(String name) {
        return getClientOpt(name)
            .orElseThrow(() -> new ClientNotFoundException(name));
    }

    List<Client> getClients();

    void deleteClient(Client client);

    AbstractAccount createAccount(Client client, Class<? extends AbstractAccount> type);

    void updateAccount(Client c, AbstractAccount account);

    AbstractAccount getAccount(Client client, Class<? extends AbstractAccount> type);

    List<AbstractAccount> getAllAccounts();

    List<AbstractAccount> getAllAccounts(Client client);

    void transferMoney(Client from, Client to, double amount);
}

@Service
@RequiredArgsConstructor
final class BankingImpl implements Banking {

    private final ClientRepository repository;
    private final ClientService clientService;

    @Override
    public Client addClient(Client client) {
      return repository.add(client);
    }

    @Override
    public Optional<Client> getClientOpt(String name) {
        return Optional.ofNullable(repository.getBy(name));
    }

    @Override
    public List<Client> getClients() {
        return repository.getAll();
    }

    @Override
    public void deleteClient(Client c) {
        repository.remove(c.getId());
    }

    @Override
    public AbstractAccount createAccount(Client c, Class<? extends AbstractAccount> type) {

        AbstractAccount account = null;

        Client client = repository.get(c.getId());

        if (client != null) {

            account = new SavingAccount(0);

            if (type == CheckingAccount.class) {
                account = new CheckingAccount(0);
            }

            clientService.addAccount(client, account);

            repository.update(c);
        }

        return account;
    }

    @Override
    public void updateAccount(Client c, AbstractAccount account) {

        Client clientToUpdate = repository.get(c.getId());

        if (clientToUpdate != null) {

            clientService.removeAccount(clientToUpdate, account.getClass());
            clientService.addAccount(clientToUpdate, account);

            repository.update(c);
        }
    }

    @Override
    public AbstractAccount getAccount(Client c, Class<? extends AbstractAccount> type) {
        return c.getAccounts().stream()
                .filter(a -> a.getClass() == type)
                .findFirst()
                .orElseThrow(AccountNotFoundException::new);
    }

    @Override
    public List<AbstractAccount> getAllAccounts() {
        return repository.getAll().stream()
                         .flatMap(c -> c.getAccounts().stream())
                         .toList();
    }

    @Override
    public List<AbstractAccount> getAllAccounts(Client c) {
        return new ArrayList<>(repository.get(c.getId()).getAccounts());
    }

    @Override
    public void transferMoney(Client from, Client to, double amount) {
        clientService.withdraw(from, amount);
        clientService.deposit(to, amount);
    }
}

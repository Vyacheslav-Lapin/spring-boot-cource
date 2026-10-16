package ru.ibs.cources.java.spring.boot.cource.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ibs.cources.java.spring.boot.cource.model.AbstractAccount;
import ru.ibs.cources.java.spring.boot.cource.model.CheckingAccount;
import ru.ibs.cources.java.spring.boot.cource.model.Client;
import ru.ibs.cources.java.spring.boot.cource.service.storage.ClientRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public sealed interface BankReportService {

    int getNumberOfBankClients();

    int getAccountsNumber();

    List<Client> getClientsSorted();

    double getBankCreditSum();

    Map<String, List<Client>> getClientsByCity();
}

@Service
@RequiredArgsConstructor
final class BankReportServiceImpl implements BankReportService {

    private final ClientRepository repository;

    @Override
    public int getNumberOfBankClients() {
        return repository.getAll().size();
    }

    @Override
    public int getAccountsNumber() {
        return Math.toIntExact(
            repository.getAll().stream()
                      .mapToLong(c -> c.getAccounts().size())
                      .sum());
    }

    @Override
    public List<Client> getClientsSorted() {
        return repository.getAll()
                         .stream()
                         .sorted(Comparator.comparing(Client::getName))
                         .collect(Collectors.toList());
    }

    @Override
    public double getBankCreditSum() {
        return repository.getAll().stream()
                         .flatMap(c -> c.getAccounts().stream())
                         .filter(a -> a.getClass() == CheckingAccount.class)
                         .mapToDouble(AbstractAccount::getBalance)
                         .filter(b -> b < 0)
                         .sum();
    }

    @Override
    public Map<String, List<Client>> getClientsByCity() {
        return repository.getAll().stream()
                         .collect(Collectors.groupingBy(Client::getCity));
    }
}

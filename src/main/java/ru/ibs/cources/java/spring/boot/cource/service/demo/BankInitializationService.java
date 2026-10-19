package ru.ibs.cources.java.spring.boot.cource.service.demo;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.ibs.cources.java.spring.boot.cource.model.Client;
import ru.ibs.cources.java.spring.boot.cource.service.Banking;

public sealed interface BankInitializationService {

    void createClientsForDemo();
}

@Service
@Profile("dev")
@RequiredArgsConstructor
final class DemoBankInitializationService implements BankInitializationService {

    private final ApplicationContext context;

    @Override
    @PostConstruct
    public void createClientsForDemo() {

        Banking banking = context.getBean(Banking.class);

        Client client_1 = (Client) context.getBean("client1");
        Client client_2 = (Client) context.getBean("client2");

        banking.addClient(client_1);
        banking.addClient(client_2);
    }
}

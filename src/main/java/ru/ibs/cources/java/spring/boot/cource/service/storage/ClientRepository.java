package ru.ibs.cources.java.spring.boot.cource.service.storage;

import ru.ibs.cources.java.spring.boot.cource.model.Client;

import java.util.List;
import java.util.UUID;

public interface ClientRepository {

    Client add(Client client);

    Client get(UUID id);

    Client getBy(String name);

    List<Client> getAll();

    Client update(Client o);

    boolean remove(UUID id);
}

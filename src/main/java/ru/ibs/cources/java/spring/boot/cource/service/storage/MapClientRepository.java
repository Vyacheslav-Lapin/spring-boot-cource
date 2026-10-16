package ru.ibs.cources.java.spring.boot.cource.service.storage;

import org.springframework.stereotype.Repository;
import ru.ibs.cources.java.spring.boot.cource.model.Client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public class MapClientRepository implements ClientRepository {

    private final Map<UUID, Client> data = new HashMap<>();

    @Override
    public Client add(Client client) {
        client.setId(UUID.randomUUID());
        data.put(client.getId(), client);

        return client;
    }

    @Override
    public Client get(UUID id) {
        return data.get(id);
    }

    @Override
    public List<Client> getAll() {
        return new ArrayList<>(data.values());
    }

    @Override
    public Client getBy(String name) { //todo 16.10.2026: Переделать на Optional
        for (Client client : getAll()) {
            if (name.equals(client.getName())) {
                return client;
            }
        }

        return null;
    }

    @Override
    public Client update(Client client) {
        return data.put(client.getId(), client); //todo 16.10.2026: убедиться что id задан!
    }

    @Override
    public boolean remove(UUID id) {
        return data.remove(id) != null;
    }
}

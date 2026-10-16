package ru.ibs.cources.java.spring.boot.cource.service.storage;

import lombok.experimental.UtilityClass;
import ru.ibs.cources.java.spring.boot.cource.model.Client;

import java.util.Arrays;

@UtilityClass
public class ClientRepositoryUtils {

  public static ClientRepository addAll(ClientRepository $this, Client... clients) {
    Arrays.stream(clients)
        .forEachOrdered($this::add);

    return $this;
  }
}

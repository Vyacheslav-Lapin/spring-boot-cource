package ru.ibs.cources.java.spring.boot.cource.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@EqualsAndHashCode(of = {"id", "name", "city"})
public class Client {

    private UUID id;

    private String name;

    private List<AbstractAccount> accounts = new ArrayList<>(2);

    private AbstractAccount activeAccount;

    private Gender gender;

    private String city;

    public Client(String name, Gender gender) {
        this.name = name;
        this.gender = gender;
    }

    @Getter
    @RequiredArgsConstructor
    public enum Gender {

        MALE("Mr"),
        FEMALE("Ms"),
        UNDEFINED("");

        private final String prefix;
    }

}

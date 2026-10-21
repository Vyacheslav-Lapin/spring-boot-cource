package ru.ibs.cources.java.spring.boot.cource.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.ExtensionMethod;
import ru.ibs.cources.java.spring.boot.cource.utils.HibernateUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static ru.ibs.cources.java.spring.boot.cource.utils.HibernateUtils.*;

@Data
@Entity
@NoArgsConstructor
@ExtensionMethod(value = HibernateUtils.class, suppressBaseMethods = false)
public class Client {

    @Id
    @GeneratedValue
    @Column(updatable = false, nullable = false)
    private UUID id;

    private String name;

    @OneToMany(targetEntity = AbstractAccount.class, fetch = FetchType.EAGER)
    private List<AbstractAccount> accounts = new ArrayList<>(2);

    @OneToOne
    private AbstractAccount activeAccount;

    private Gender gender;

    private String city;

    public Client(String name, Gender gender) {
        this.name = name;
        this.gender = gender;
    }

    //region equals and hashCode
    @Override
    public boolean equals(Object object) {
        return this == object || object instanceof Client client
                                 && persistentClass(this) == client.persistentClass()
                                 && Objects.equals(getId(), client.getId());
    }

    @Override
    public int hashCode() {
        return persistentClass(this).hashCode();
    }
    //endregion

    @Getter
    @RequiredArgsConstructor
    public enum Gender {

        MALE("Mr"),
        FEMALE("Ms"),
        UNDEFINED("");

        private final String prefix;
    }

}

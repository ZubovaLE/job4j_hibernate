package ru.job4j.hql.student;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    private String username;

    private boolean active;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AccountBook> accountBooks = new ArrayList<>();

    public void addBook(AccountBook accountBook) {
        this.accountBooks.add(accountBook);
    }

    public static Account of(String username) {
        Account a = new Account();
        a.username = username;
        a.active = true;
        return a;
    }

    @Override
    public String toString() {
        return String.format("Account: id=%s, username=%s, active=%s, books=%s", id, username, active, accountBooks);
    }
}

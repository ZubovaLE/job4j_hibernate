package ru.job4j.hql.student;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "accbooks")
public class AccountBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    private String name;

    private String publishingHouse;

    public static AccountBook of(String name, String publishingHouse) {
        AccountBook b = new AccountBook();
        b.name = name;
        b.publishingHouse = publishingHouse;
        return b;
    }

    @Override
    public String toString() {
        return String.format("Book: id=%s, name=%s, publishingHouse=%s", id, name, publishingHouse);
    }
}
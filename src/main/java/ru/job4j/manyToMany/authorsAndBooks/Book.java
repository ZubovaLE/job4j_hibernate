package ru.job4j.manyToMany.authorsAndBooks;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;
    private String name;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Author> authors = new HashSet<>();

    public void addAuthor(Author author) {
        this.authors.add(author);
    }

    public static Book of(String name) {
        Book book = new Book();
        book.name = name;
        return book;
    }
}
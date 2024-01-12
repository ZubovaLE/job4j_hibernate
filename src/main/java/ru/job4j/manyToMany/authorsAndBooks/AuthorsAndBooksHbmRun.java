package ru.job4j.manyToMany.authorsAndBooks;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.manyToMany.personsAndAddresses.Address;
import ru.job4j.manyToMany.personsAndAddresses.Person;

public class AuthorsAndBooksHbmRun {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            Book one = Book.of("Tom 1");
            Book two = Book.of("Tom 2");
            session.save(one);
            session.save(two);

            Author first = Author.of("M.J.Collins");
            first.addBook(one);
            first.addBook(two);

            Author second = Author.of("K.L.Poll");
            second.addBook(two);

            session.persist(first);
            session.persist(second);

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}

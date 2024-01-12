package ru.job4j.manyToMany.authorsAndBooks;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class AuthorsAndBooksHbmRun {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            Author one = Author.of("Author 1");
            Author two = Author.of("Author 2");
            Author three = Author.of("Author 3");
            session.persist(one);
            session.persist(two);
            session.persist(three);

            Book first = Book.of("Book 1");
            first.addAuthor(one);
            first.addAuthor(two);
            first.addAuthor(three);

            Book second = Book.of("Book 2");
            second.addAuthor(two);
            second.addAuthor(three);

            Book third = Book.of("Book 3");
            third.addAuthor(two);

            session.persist(first);
            session.persist(second);
            session.persist(third);

//            Book book = session.get(Book.class, 3);
//            session.remove(book);

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
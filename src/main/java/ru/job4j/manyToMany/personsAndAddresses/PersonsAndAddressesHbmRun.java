package ru.job4j.manyToMany.personsAndAddresses;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class PersonsAndAddressesHbmRun {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            Address one = Address.of("Kazanskaya", "1");
            Address two = Address.of("Piterskaya", "10");
            session.save(one);
            session.save(two);

            Person first = Person.of("Bob");
            first.addAddress(one);
            first.addAddress(two);

            Person second = Person.of("Alex");
            second.addAddress(two);

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
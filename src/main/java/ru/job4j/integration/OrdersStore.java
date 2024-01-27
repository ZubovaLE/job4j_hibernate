package ru.job4j.integration;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class OrdersStore {
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();

    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    public Order save(Order order) {
        return this.tx(
                session -> {
                    session.save(order);
                    return order;
                }
        );
    }

    public List<Order> findAll() {
        List<Order> rsl = new ArrayList<>();
        return this.tx(
                session -> session.createQuery("from Order ").list()
        );
    }

    public Order findById(int id) {
        return this.tx(
                session -> session.get(Order.class, id)
        );
    }

    public Order findByName(String name) {
        return this.tx(
                session -> session.createQuery("from Order where name = :oName ", Order.class)
                        .setParameter("oName", name)
                        .uniqueResult()
        );
    }

    public void update(int id, Order order) {
        Session session = sf.openSession();
        session.beginTransaction();
        order.setId(id);
        session.update(order);
        session.getTransaction().commit();
        session.close();
    }

    private <T> T tx(final Function<Session, T> command) {
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }
}
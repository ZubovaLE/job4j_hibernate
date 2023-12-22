package ru.job4j.hql.candidate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

public class HbmRun {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            Candidate one = Candidate.of("Bob", 2, 123000.0);
            Candidate two = Candidate.of("Emily", 1, 85000.35);
            Candidate three = Candidate.of("John", 5, 237000.47);

            session.save(one);
            session.save(two);
            session.save(three);
            session.getTransaction().commit();

            Query query = session.createQuery("from Candidate");
            for (Object c : query.list()) {
                System.out.println(c);
            }
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}

package ru.job4j.hql.candidate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import java.util.List;

public class HbmRun {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

//            Candidate one = Candidate.of("Bob", 2, 123000.0);
//            Candidate two = Candidate.of("Emily", 1, 85000.35);
//            Candidate three = Candidate.of("John", 5, 237000.47);
//            Candidate four = Candidate.of("Anna", 3, 214000.32);
//
//            session.save(one);
//            session.save(two);
//            session.save(three);
//            session.save(four);
//            session.getTransaction().commit();

            //SELECT all
            Query querySelectAll = session.createQuery("from Candidate");
            for (Object c : querySelectAll.list()) {
                System.out.println(c);
            }

            //SELECT by id
            Query querySelectById = session.createQuery("from Candidate where id in :ids")
                    .setParameter("ids", List.of(1, 3));
            for (Object c : querySelectById.list()) {
                System.out.println(c);
            }

            //SELECT by name
            System.out.println(session.createQuery("from Candidate where name = :name")
                    .setParameter("name", "Anna")
                    .uniqueResult());

            //SELECT by experience
            Query querySelectByExperience = session.createQuery("from Candidate where experience > 2");
            for (Object c : querySelectByExperience.list()) {
                System.out.println(c);
            }

            //SELECT by salary
            Query querySelectBySalary = session.createQuery("from Candidate where salary > 100000.0");
            for (Object c : querySelectBySalary.list()) {
                System.out.println(c);
            }

            //UPDATE candidate with id = 3
            session.createQuery("update Candidate c set c.experience = 6 where c.id = :cid")
                    .setParameter("cid", 3)
                    .executeUpdate();

            //DELETE candidate with id = 1
            session.createQuery("delete from Candidate where id = :cid")
                    .setParameter("cid", 1)
                    .executeUpdate();

            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
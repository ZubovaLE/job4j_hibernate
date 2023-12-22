package ru.job4j.hql.student;

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

//            Student one = Student.of("Alex", 21, "Moscow");
//            Student two = Student.of("Nikolay", 28, "Saint-Petersburg");
//            Student three = Student.of("Nikita", 25, "Kaliningrad");
//
//            session.save(one);
//            session.save(two);
//            session.save(three);
//            session.getTransaction().commit();

            //SELECT
            Query query = session.createQuery("from Student");
            for (Object st : query.list()) {
                System.out.println(st);
            }

            //SELECT with a condition
            System.out.println(session.createQuery("from Student s where s.id =:fid")
                    .setParameter("fid", 1).uniqueResult());

            //UPDATE
            session.createQuery("update Student s set s.age = :newAge, s.city = :newCity where s.id = :fid")
                    .setParameter("newAge", 22)
                    .setParameter("newCity", "London")
                    .setParameter("fid", 1)
                    .executeUpdate();

            //DELETE
            session.createQuery("delete from Student where id = :fid")
                    .setParameter("fid", 3)
                    .executeUpdate();

            //INSERT
            session.createQuery("insert into Student (name, age, city)"
                            + "select concat(s.name, 'NEW'), s.age + 5, s.city "
                            + "from Student s where s.id = :fid")
                    .setParameter("fid", 1)
                    .executeUpdate();

            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}

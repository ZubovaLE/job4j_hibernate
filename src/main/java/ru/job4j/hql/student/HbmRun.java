package ru.job4j.hql.student;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HbmRun {
    public static void main(String[] args) {
        Student rsl = null;
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            AccountBook first = AccountBook.of("Book111112", "House xxx");
            session.save(first);
            AccountBook second = AccountBook.of("Book2222223", "House nnn");
            session.save(second);

            Account accountOne = Account.of("account One");
            accountOne.addBook(session.load(AccountBook.class, first.getId()));
            session.save(accountOne);

            Account accountTwo = Account.of("account Two");
            accountTwo.addBook(session.load(AccountBook.class, second.getId()));
            session.save(accountTwo);

            Student studentOne = Student.of("Ken", 22, "Moscow");
            studentOne.setAccount(session.load(Account.class, accountOne.getId()));
            session.save(studentOne);

            Student studentTwo = Student.of("Emily", 33, "Saint-Petersburg");
            studentTwo.setAccount(session.load(Account.class, accountTwo.getId()));
            session.save(studentTwo);

            rsl = session.createQuery(
                    "select distinct st from Student st "
                            + "join fetch st.account a "
                            + "join fetch a.accountBooks b "
                            + "where st.id = :sId", Student.class
            ).setParameter("sId", 1).uniqueResult();

            session.getTransaction().commit();

//            //SELECT
//            Query query = session.createQuery("from Student");
//            for (Object st : query.list()) {
//                System.out.println(st);
//            }
//
//            //SELECT with a condition
//            System.out.println(session.createQuery("from Student s where s.id =:fid")
//                    .setParameter("fid", 1).uniqueResult());
//
//            //UPDATE
//            session.createQuery("update Student s set s.age = :newAge, s.city = :newCity where s.id = :fid")
//                    .setParameter("newAge", 22)
//                    .setParameter("newCity", "London")
//                    .setParameter("fid", 1)
//                    .executeUpdate();
//
//            //DELETE
//            session.createQuery("delete from Student where id = :fid")
//                    .setParameter("fid", 3)
//                    .executeUpdate();
//
//            //INSERT
//            session.createQuery("insert into Student (name, age, city)"
//                            + "select concat(s.name, 'NEW'), s.age + 5, s.city "
//                            + "from Student s where s.id = :fid")
//                    .setParameter("fid", 1)
//                    .executeUpdate();

            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
        System.out.println(rsl);
    }
}

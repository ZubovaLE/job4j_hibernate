package ru.job4j.hql.candidate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HbmRun {
    public static void main(String[] args) {
        Candidate result = null;
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            Vacancy vacancyOne = Vacancy.of("vacancy 1", "Do work very well", 200000.0);
            session.save(vacancyOne);
            Vacancy vacancyTwo = Vacancy.of("vacancy 2", "Do work very good", 100000.0);
            session.save(vacancyTwo);
            Vacancy vacancyThree = Vacancy.of("vacancy 3", "Do work", 180000.0);
            session.save(vacancyThree);

            VacancyBase baseOne = VacancyBase.of("base one");
            baseOne.addVacancy(session.load(Vacancy.class, vacancyOne.getId()));
            baseOne.addVacancy(session.load(Vacancy.class, vacancyThree.getId()));
            session.save(baseOne);

            VacancyBase baseTwo = VacancyBase.of("base two");
            baseTwo.addVacancy(session.load(Vacancy.class, vacancyTwo.getId()));
            session.save(baseTwo);

            Candidate candidateOne = Candidate.of("Mark", 5, 127000.0);
            candidateOne.setVacancyBase(session.load(VacancyBase.class, baseTwo.getId()));
            session.save(candidateOne);

            Candidate candidateTwo = Candidate.of("Anne", 2, 89000.35);
            candidateOne.setVacancyBase(session.load(VacancyBase.class, baseOne.getId()));
            session.save(candidateTwo);

            result = session.createQuery(
                            "select distinct c from Candidate c " +
                                    "join fetch c.vacancyBase vb " +
                                    "join fetch vb.vacancies v " +
                                    "where c.id = :cId", Candidate.class
                    ).setParameter("cId", candidateOne.getId())
                    .uniqueResult();

// Old task
//            //SELECT all
//            Query querySelectAll = session.createQuery("from Candidate");
//            for (Object c : querySelectAll.list()) {
//                System.out.println(c);
//            }
//
//            //SELECT by id
//            Query querySelectById = session.createQuery("from Candidate where id in :ids")
//                    .setParameter("ids", List.of(1, 3));
//            for (Object c : querySelectById.list()) {
//                System.out.println(c);
//            }
//
//            //SELECT by name
//            System.out.println(session.createQuery("from Candidate where name = :name")
//                    .setParameter("name", "Anna")
//                    .uniqueResult());
//
//            //SELECT by experience
//            Query querySelectByExperience = session.createQuery("from Candidate where experience > 2");
//            for (Object c : querySelectByExperience.list()) {
//                System.out.println(c);
//            }
//
//            //SELECT by salary
//            Query querySelectBySalary = session.createQuery("from Candidate where salary > 100000.0");
//            for (Object c : querySelectBySalary.list()) {
//                System.out.println(c);
//            }
//
//            //UPDATE candidate with id = 3
//            session.createQuery("update Candidate c set c.experience = 6 where c.id = :cid")
//                    .setParameter("cid", 3)
//                    .executeUpdate();
//
//            //DELETE candidate with id = 1
//            session.createQuery("delete from Candidate where id = :cid")
//                    .setParameter("cid", 1)
//                    .executeUpdate();
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
        System.out.println(result);
    }
}
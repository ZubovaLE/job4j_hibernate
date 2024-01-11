package ru.job4j.oneToMany;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class OneToManyHbmRun {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            Car qashqai = Car.of("Qashqai");
            session.save(qashqai);
            Car xTrail = Car.of("X-Trail");
            session.save(xTrail);
            Car almera = Car.of("Almera");
            session.save(almera);
            Car juke = Car.of("Juke");
            session.save(juke);
            Car murano = Car.of("Murano");
            session.save(murano);

            Brand nissan = Brand.of("Nissan");
            nissan.addCar(session.load(Car.class, qashqai.getId()));
            nissan.addCar(session.load(Car.class, xTrail.getId()));
            nissan.addCar(session.load(Car.class, almera.getId()));
            nissan.addCar(session.load(Car.class, juke.getId()));
            nissan.addCar(session.load(Car.class, murano.getId()));
            session.save(nissan);

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
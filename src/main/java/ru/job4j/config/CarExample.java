package ru.job4j.config;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "config_cars")
public class CarExample {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String model;
    private Timestamp created;
    private String owner;

    public static CarExample of(String model, Timestamp created, String owner) {
        CarExample car = new CarExample();
        car.model = model;
        car.created = created;
        car.owner = owner;
        return car;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarExample car = (CarExample) o;
        return id == car.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
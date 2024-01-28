package ru.job4j.config;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "config_cars")
public class CarExample {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
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
}
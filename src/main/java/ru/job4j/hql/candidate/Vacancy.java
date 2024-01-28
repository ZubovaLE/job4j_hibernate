package ru.job4j.hql.candidate;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "vacancies")
public class Vacancy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    private String name;

    private String description;

    private double salary;

    public static Vacancy of(String name, String description, double salary) {
        Vacancy vacancy = new Vacancy();
        vacancy.name = name;
        vacancy.description = description;
        vacancy.salary = salary;
        return vacancy;
    }

    @Override
    public String toString() {
        return "Vacancy{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", salary=" + salary +
                '}';
    }
}
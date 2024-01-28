package ru.job4j.hql.candidate;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "candidates")
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;
    private String name;
    private int experience;
    private double salary;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vacancy_base_id")
    private VacancyBase vacancyBase;

    public static Candidate of(String name, int experience, double salary) {
        Candidate candidate = new Candidate();
        candidate.name = name;
        candidate.experience = experience;
        candidate.salary = salary;
        return candidate;
    }

    @Override
    public String toString() {
        return String.format("Candidate: id=%d, name=%s, experience=%d, salary=%f, vacancyBase=%s", id, name,
                experience, salary, vacancyBase);
    }
}
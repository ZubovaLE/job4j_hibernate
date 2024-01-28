package ru.job4j.hql.candidate;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "base")
public class VacancyBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vacancy> vacancies = new ArrayList<>();

    public void addVacancy(Vacancy vacancy) {
        this.vacancies.add(vacancy);
    }

    public static VacancyBase of(String name) {
        VacancyBase base = new VacancyBase();
        base.name = name;
        return base;
    }

    @Override
    public String toString() {
        return "VacancyBase{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", vacancies=" + vacancies +
                '}';
    }
}
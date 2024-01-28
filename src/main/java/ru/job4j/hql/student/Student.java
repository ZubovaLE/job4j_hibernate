package ru.job4j.hql.student;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;
    private String name;
    private int age;
    private String city;

    @OneToOne(fetch = FetchType.LAZY)
    private Account account;

    public static Student of(String name, int age, String city) {
        Student student = new Student();
        student.name = name;
        student.age = age;
        student.city = city;
        return student;
    }

    @Override
    public String toString() {
        return String.format("Student: id=%s, name=%s, age=%s, city=%s, account=%s", id, name, age, city, account);
    }
}
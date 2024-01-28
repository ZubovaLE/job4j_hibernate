package ru.job4j.many;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "role_to_many")
public class RoleToMany {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserToMany> users = new ArrayList<>();

    public static RoleToMany of(String name) {
        RoleToMany role = new RoleToMany();
        role.name = name;
        return role;
    }

    public void addUser(UserToMany u) {
        this.users.add(u);
    }
}
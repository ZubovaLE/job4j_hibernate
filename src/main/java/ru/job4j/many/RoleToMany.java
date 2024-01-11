package ru.job4j.many;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "role_to_many")
@Getter
@Setter
public class RoleToMany {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleToMany role = (RoleToMany) o;
        return id == role.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
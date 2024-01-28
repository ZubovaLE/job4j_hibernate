package ru.job4j.lazy;

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
@Table(name = "car_brands")
public class CarBrand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;
    private String name;

    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL)
    private List<CarModel> models = new ArrayList<>();

    public static CarBrand of(String name) {
        CarBrand brand = new CarBrand();
        brand.name = name;
        return brand;
    }

    @Override
    public String toString() {
        return "CarBrand{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
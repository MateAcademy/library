package ua.klunniy.spring.models.hibernate2;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author Serhii Klunniy
 * Это покупатели
 */
@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
@Table(name = "Buyers", schema = "hibernate2")
public class Buyers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(name = "name")
    String name;

    @Column(name = "age")
    int age;

//list purchase - лист товаров
    @OneToMany(mappedBy = "buyers")
    @ToString.Exclude
    List<Purchase> purchaseList;

    public Buyers(String name, int age) {
        this.name = name;
        this.age = age;
    }

}

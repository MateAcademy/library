package ua.klunniy.spring.hibernate.models.oneToMany.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Person", schema = "hibernate1")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private String age;

    @OneToMany(mappedBy = "owner")
    private List<Item> items;

    public Person(String name, String age) {
        this.name = name;
        this.age = age;
    }

}

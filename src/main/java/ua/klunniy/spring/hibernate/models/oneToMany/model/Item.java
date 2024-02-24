package ua.klunniy.spring.hibernate.models.oneToMany.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * @author Serhii Klunniy
 */
@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Item", schema = "hibernate1")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "person_id")
    private Person owner;

    public Item(String name, Person owner) {
        this.name = name;
        this.owner = owner;
    }

    public Item(String name) {
        this.name = name;
    }

    public Item(Person owner) {
        this.owner = owner;
    }

}

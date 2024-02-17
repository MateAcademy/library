package ua.klunniy.spring.hibernate.models.hibernate1;

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
@Table(name = "Item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private long id;

    @Column(name = "item_name")
    private String name;

//    @ManyToOne
//    @JoinColumn(name = "student_id", referencedColumnName = "student_id")
    @Transient
    private Students owner;

    public Item(String name) {
        this.name = name;
    }

    public Item(String name, Students owner) {
        this.name = name;
        this.owner = owner;
    }

}

package ua.klunniy.spring.models.hibernate2;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * @author Serhii Klunniy
 * Это Заказ:
 */
@Getter
@Setter
@Entity
@ToString
@Table(name = "Purchase", schema = "hibernate2")
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(name = "name")
    String name;

    @ManyToOne
    @JoinColumn(name = "buyers_id", referencedColumnName = "id")
    Buyers buyers;

    public Purchase(String name) {
        this.name = name;
    }

}

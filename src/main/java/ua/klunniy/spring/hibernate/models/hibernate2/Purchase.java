package ua.klunniy.spring.hibernate.models.hibernate2;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
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

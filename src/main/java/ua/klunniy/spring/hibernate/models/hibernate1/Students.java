package ua.klunniy.spring.hibernate.models.hibernate1;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * @author Serhii Klunniy
 */
@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Students")
public class Students {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private Integer age;

//    @OneToMany(mappedBy = "owner")
//    @ToString.Exclude
    private List<Item> items;

    public Students(String name) {
        this.name = name;
    }

}

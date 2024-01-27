package ua.klunniy.spring.models;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.proxy.HibernateProxy;

import jakarta.persistence.*;
import javax.validation.constraints.*;
import java.util.Objects;

/**
 * @author Serhii Klunniy
 */
@Getter
@Setter
@ToString
@Entity
@Table(name = "Person")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Person {

    @Id
    @Column(name = "person_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "first_name")
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    String firstName;

    @Column(name = "last_name")
    @NotEmpty(message = "Surname should not be empty")
    @Size(min = 2, max = 30, message = "Surname should be between 2 and 30 characters")
    String lastName;

    @Column(name = "patronymic")
    @NotEmpty(message = "Patronymic should not be empty")
    @Size(min = 2, max = 30, message = "Patronymic should be between 2 and 30 characters")
    String patronymic;

    @Column(name = "age")
    @Min(value = 0, message = "Age should be greater than 0")
    int age;

    @Column(name = "email")
    @NotEmpty(message = "Email should not be empty")
    @Size(min = 2, max = 30, message = "Email should be between 2 and 30 characters")
    @Email(message ="Email should be valid")
    String email;

//Страна, Город, Индекс(6 цифр)
    @NotEmpty(message = "Address should not be empty")
    @Size(min = 6, max = 30, message = "Address should be between 6 and 30 characters")
    @Pattern(regexp = "[A-Z]\\w+, [A-Z]\\w+, \\d{6}", message = "Your address should be in this format: Country, City, Postal Code (6 digits)")
    String address;

    public Person() {
    }

    public Person(String firstName, String lastName, String patronymic, int age, String email, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.age = age;
        this.email = email;
        this.address = address;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Person person = (Person) o;
        return getId() != null && Objects.equals(getId(), person.getId());
    }

    @Override
    public final int hashCode() {
        return getClass().hashCode();
    }

}

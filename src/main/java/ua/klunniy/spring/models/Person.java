package ua.klunniy.spring.models;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Cascade;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.format.annotation.DateTimeFormat;
import ua.klunniy.spring.enums.Mood;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import javax.validation.constraints.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author Serhii Klunniy
 */
@Getter
@Setter
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
    @Email(message = "Email should be valid")
    String email;

    //Страна, Город, Индекс(6 цифр)
    @NotEmpty(message = "Address should not be empty")
    @Size(min = 6, max = 30, message = "Address should be between 6 and 30 characters")
    @Pattern(regexp = "[A-Z]\\w+, [A-Z]\\w+, \\d{6}", message = "Your address should be in this format: Country, City, Postal Code (6 digits)")
    String address;

    @Column(name = "date_of_birth")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yy")
    Date dateOfBirth;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    Date createdAt;

    @Enumerated(EnumType.STRING)    //выдает индекс начиная с 0
    @Column(name = "mood")
    Mood mood;

    @OneToMany(mappedBy = "person", fetch = FetchType.LAZY)
    // @Cascade(org.hibernate.annotations.CascadeType.ALL)
    List<Book> bookList;

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

    public Person(long personId, String firstName, String lastName, String patronymic, int age, String email, String address) {
        this.id = personId;
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

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

}

package ua.klunniy.spring.models;

import javax.persistence.*;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * @author Serhii Klunniy
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    Long bookId;

    @Column(name = "person_id")
    Long personId;

    @NotEmpty(message = "Book name should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    @Column(name = "name_book")
    String nameBook;

    @NotEmpty(message = "Book description should not be empty")
    @Size(min = 2, max = 300, message = "Description should be between 2 and 300 characters")
    @Column(name = "author")
    String author;

    @Min(value = 1900, message = "Year should be more 1900")
    @Column(name = "year")
    int year;

    public Book(String nameBook, String author, int year) {
        this.nameBook = nameBook;
        this.author = author;
        this.year = year;
    }

    public Book(Long personId, String nameBook, String author, int year) {
        this.personId = personId;
        this.nameBook = nameBook;
        this.author = author;
        this.year = year;
    }

}

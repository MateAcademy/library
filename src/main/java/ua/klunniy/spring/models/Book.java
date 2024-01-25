package ua.klunniy.spring.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * @author Serhii Klunniy
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    private Long bookId;

    private Long personId;

    @NotEmpty(message = "Book name should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    private String nameBook;

    @NotEmpty(message = "Book description should not be empty")
    @Size(min = 2, max = 300, message = "Description should be between 2 and 300 characters")
    private String author;

    @Min(value = 1900, message = "Year should be more 1900")
    private int year;

    public Book(String nameBook, String author, int year) {
        this.nameBook = nameBook;
        this.author = author;
        this.year = year;
    }

    public Book(long personId, String nameBook, String author, int year) {
        this.personId = personId;
        this.nameBook = nameBook;
        this.author = author;
        this.year = year;
    }

}

package ua.klunniy.spring.models;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * @author Serhii Klunniy
 */
@Data
public class Book {

    private int id;

    @NotEmpty(message = "Book name should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    private String name;

    @NotEmpty(message = "Book description should not be empty")
    @Size(min = 2, max = 300, message = "Description should be between 2 and 300 characters")
    private String description;

    public Book() {
    }

    public Book(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Book(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

}

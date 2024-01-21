package ua.klunniy.spring.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.klunniy.spring.models.Book;
import ua.klunniy.spring.service.BookService;

/**
 * @author Serhii Klunniy
 */
@Component
public class BookValidator implements Validator {

    private final BookService bookService;

    @Autowired
    public BookValidator(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Book.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        Book book = (Book) target;
        String bookName = book.getName();

        if (bookService.show(bookName) != null) {
            errors.rejectValue("name", "", "In db is present such book name");
        }

    }

}

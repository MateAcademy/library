package ua.klunniy.spring.util.validator;

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
    public boolean supports(Class<?> aClass) {
        return Book.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
//todo: какая то дебильная валидация
//1) должна быть проверка на уникальность для name_book, author, year
//2) должна быть проверка на null для name_book и для author, поле не должно равняться null
//3) должна быть проверка на year>1900
        Book book = (Book) o;
        String bookName = book.getNameBook();
        String author = book.getAuthor();
        int year = book.getYear();

        Book bookFromDb = bookService.getBookByNaneAuthorYear(bookName, author, year);

        if (bookFromDb != null) {

        } else {

        }



        if (bookService.getBookById(bookName, author, year) != null) {
            errors.rejectValue("name", "", "In db is present such book name");
        }

    }

}

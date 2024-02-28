package ua.klunniy.spring.dao.impl.jdbc;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ua.klunniy.spring.dao.BookDao;
import ua.klunniy.spring.models.Book;

import java.util.List;
import java.util.Optional;

/**
 * @author Serhii Klunniy
 */
@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookDaoJdbcImpl implements BookDao {
    @Override
    public List<Book> index() {
        return null;
    }

    @Override
    public List<Book> getListBooksByPersonId(Long personId) {
        return null;
    }

    @Override
    public Book show(long id) {
        return null;
    }

    public Optional<Book> show(String name) {
        return Optional.empty();
    }

    @Override
    public Optional<Book> show(String bookName, String author, int year) {
        return Optional.empty();
    }

    @Override
    public void save(Book book) {
        System.out.println("jdbc");
    }

    @Override
    public void update(long id, Book updateBook) {

    }

    @Override
    public void releaseTheBookFromThePerson(Long bookId) {

    }

    @Override
    public void delete(long id) {

    }

    @Override
    public void setPersonId(Long bookId, Long personId) {

    }

    @Override
    public Book getBookByNaneAuthorYear(String bookName, String author, int year) {
        return null;
    }
}

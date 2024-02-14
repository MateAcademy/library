package ua.klunniy.spring.dao;

import ua.klunniy.spring.models.Book;

import java.util.List;
import java.util.Optional;

/**
 * @author Serhii Klunniy
 */
public interface BookDao {
    List<Book> index();

    List<Book> getListBooksByPersonId(Long personId);

    Book show(long id);

//todo: может быть этот метод удалить?
    Optional<Book> show(String name);
    Optional<Book> show(String bookName, String author, int year);
    void save(Book book);
    void update(long id, Book updateBook);
    void releaseTheBookFromThePerson(Long bookId);
    void delete(int id);
    void setPersonId(Long bookId, Long personId);
    Book getBookByNaneAuthorYear(String bookName, String author, int year);

}

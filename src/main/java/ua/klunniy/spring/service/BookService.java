package ua.klunniy.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.klunniy.spring.dao.BookDAO;
import ua.klunniy.spring.models.Book;

import java.util.List;

/**
 * @author Serhii Klunniy
 */
@Service
public class BookService {
    private final BookDAO bookDAO;

    @Autowired
    public BookService(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    public List<Book> index() {
        return bookDAO.index();
    }

    public Book getBookById(Long bookId) {
        if (bookId == null) {
            return null;
        }
        return bookDAO.show(bookId);
    }

    public List<Book> getListBooksByPersonId(Long personId) {
        if (personId == null) {
            return null;
        }
        return bookDAO.getListBooksByPersonId(personId);
    }

//    public Book getBookById(String name) {
//        return bookDAO.show(name).stream().findAny().orElse(null);
//    }

    public void releaseTheBookFromThePerson(Long bookId) {
        bookDAO.releaseTheBookFromThePerson(bookId);
    }

    public Book getBookById(String bookName, String author, int year) {
        return  bookDAO.show(bookName, author, year).stream().findAny().orElse(null);
    }

    public void save(Book book) {
        if (book != null) {
            bookDAO.save(book);
        }
    }

    public void update(long id, Book book) {
        if (book != null) {
            bookDAO.update(id, book);
        }
    }

    public void delete(int id) {
        bookDAO.delete(id);
    }

    public void setPersonId(Long bookId, Long personId) {
        bookDAO.setPersonId(bookId, personId);
    }

}


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

    public Book show(int id) {
        return bookDAO.show(id);
    }

    public Book show(String name) {
        return bookDAO.show(name).stream().findAny().orElse(null);
    }

    public void save(Book book) {
        if (book != null) {
            bookDAO.save(book);
        }
    }

    public void update(int id, Book book) {
        if (book != null) {
            bookDAO.update(id, book);
        }
    }

    public void delete(int id) {
        bookDAO.delete(id);
    }

}


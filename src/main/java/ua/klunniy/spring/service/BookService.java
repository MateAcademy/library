package ua.klunniy.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ua.klunniy.spring.dao.BookDao;
import ua.klunniy.spring.models.Book;

import java.util.List;

/**
 * @author Serhii Klunniy
 */
@Service
public class BookService {

    @Autowired
    @Qualifier("bookDaoHibernate")
    private BookDao bookDao;
//    private final BookDaoJdbcTemplateImpl bookDaoJdbcTemplateImpl;
//
//    @Autowired
//    public BookService(BookDaoJdbcTemplateImpl bookDaoJdbcTemplateImpl) {
//        this.bookDaoJdbcTemplateImpl = bookDaoJdbcTemplateImpl;
//    }

    public List<Book> index() {
        return bookDao.index();
    }

    public Book getBookById(Long bookId) {
        if (bookId == null) {
            return null;
        }
        return bookDao.show(bookId);
    }

    public Book getBookByNaneAuthorYear(String nameBook, String author, int year) {
        return bookDao.getBookByNaneAuthorYear(nameBook, author, year);
    }
    public List<Book> getBooksByPersonId(Long personId) {
        if (personId == null) {
            return null;
        }
        return bookDao.getListBooksByPersonId(personId);
    }

//    public Book getBookById(String name) {
//        return bookDAO.show(name).stream().findAny().orElse(null);
//    }

    public void releaseTheBookFromThePerson(Long bookId) {
        bookDao.releaseTheBookFromThePerson(bookId);
    }

    public Book getBookById(String bookName, String author, int year) {
        return bookDao.show(bookName, author, year).stream().findAny().orElse(null);
    }

    public void save(Book book) {
        if (book != null) {
            bookDao.save(book);
        }
    }

    public void update(long id, Book book) {
        if (book != null) {
            bookDao.update(id, book);
        }
    }

    public void delete(long id) {
        bookDao.delete(id);
    }

    public void setPersonId(Long bookId, Long personId) {
        bookDao.setPersonId(bookId, personId);
    }

}


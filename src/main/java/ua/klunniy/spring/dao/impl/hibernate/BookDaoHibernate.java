package ua.klunniy.spring.dao.impl.hibernate;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ua.klunniy.spring.dao.BookDao;
import ua.klunniy.spring.models.Book;

import java.util.List;
import java.util.Optional;

/**
 * @author Serhii Klunniy
 */
@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookDaoHibernate implements BookDao {

    final SessionFactory sessionFactory;

    @Autowired
    public BookDaoHibernate(@Qualifier("hibernateSessionFactory") SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    //  что бы получить данные из таблицы мы используем метод query
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public List<Book> index() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Book", Book.class).getResultList();
    }

    //  что бы получить данные из таблицы мы используем метод query
    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public List<Book> getListBooksByPersonId(Long personId) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM Book WHERE personId = :person_id";
        Query query = session.createQuery(hql);
        query.setParameter("person_id", personId);
        List<Book> books = query.list();
        return books;
    }

    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public Book show(long myBookIdValue) {
        Session session = sessionFactory.getCurrentSession();
//        String hql = "FROM Book WHERE bookId = :book_id";
//        Query query = session.createQuery(hql);
//        query.setParameter("book_id", myBookIdValue);
//        Book book = (Book) query.uniqueResult();
        return session.get(Book.class, myBookIdValue);
    }

    @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true)
    public Optional<Book> show(String yourNameValue, String yourAuthorValue, int yourYearValue) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM Book WHERE nameBook = :name AND author = :author AND year = :year";
        Query query = session.createQuery(hql);
        query.setParameter("name", yourNameValue);
        query.setParameter("author", yourAuthorValue);
        query.setParameter("year", yourYearValue);

        Book book = (Book) query.uniqueResult();
        Optional<Book> bookOptional = Optional.ofNullable(book);
        return bookOptional;
    }

    @Transactional(transactionManager = "hibernateTransactionManager")
    public void save(Book book) {
        Session session = sessionFactory.getCurrentSession();
        session.save(book);
    }

    @Transactional(transactionManager = "hibernateTransactionManager")
    public void update(long id, Book updateBook) {
        Session session = sessionFactory.getCurrentSession();
        Book book = session.get(Book.class, id);
        book.setNameBook(updateBook.getNameBook());
        book.setAuthor(updateBook.getAuthor());
        book.setYear(updateBook.getYear());
//        String hql = "UPDATE Book SET nameBook = :name, author = :author, year = :year WHERE bookId = :bookId";
//        Query query = session.createQuery(hql);
//        query.setParameter("name", updateBook.getNameBook());
//        query.setParameter("author", updateBook.getAuthor());
//        query.setParameter("year", updateBook.getYear());
//        query.setParameter("bookId", id);
//        query.executeUpdate();
    }

    @Transactional(transactionManager = "hibernateTransactionManager")
    public void releaseTheBookFromThePerson(Long bookId) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "UPDATE Book SET personId = :personId WHERE bookId = :bookId";
        Query query = session.createQuery(hql);
        query.setParameter("personId", null);
        query.setParameter("bookId", bookId);
        query.executeUpdate();
    }

    @Transactional(transactionManager = "hibernateTransactionManager")
    public void delete(long idBook) {
        Session session = sessionFactory.getCurrentSession();
//        String sql = "DELETE FROM Book WHERE bookId = :bookId";
//        Query query = session.createQuery(sql);
//        query.setParameter("bookId", idBook);
//        query.executeUpdate();
        session.remove(session.get(Book.class, idBook));
    }

    @Transactional(transactionManager = "hibernateTransactionManager")
    public void setPersonId(Long bookId, Long personId) {
        Session session = sessionFactory.getCurrentSession();
        String sql = "UPDATE Book SET personId = :person_id where bookId = :book_id";
        Query query = session.createQuery(sql);
        query.setParameter("person_id", personId);
        query.setParameter("book_id", bookId);
        query.executeUpdate();
    }

    @Transactional(transactionManager = "hibernateTransactionManager")
    public Book getBookByNaneAuthorYear(String nameBook, String author, int year) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "SELECT b from Book b where nameBook= :name_book and author= :author and year =:year";
        Query query = session.createQuery(hql);
        query.setParameter("name_book", nameBook);
        query.setParameter("author", author);
        query.setParameter("year", year);
        Book book = (Book) query.uniqueResult();
        return book;
    }

}

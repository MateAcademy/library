package ua.klunniy.spring.dao;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ua.klunniy.spring.models.Book;
import ua.klunniy.spring.models.BookRowMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Serhii Klunniy
 */
@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookDAO {

    final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index() {
//        return jdbcTemplate.query("SELECT * from Book", new BeanPropertyRowMapper<>(Book.class));
        return jdbcTemplate.query("SELECT * from Book order by book_id", new BookRowMapper());
    }

    public List<Book> getListBooksByPersonId(Long personId) {
       return jdbcTemplate.query("SELECT * from Book where person_id=?", new Object[]{personId},
                        new BookRowMapper())
                .stream().collect(Collectors.toList());
    }

    public Book show(long id) {
        return jdbcTemplate.query("SELECT * from Book where book_id=?", new Object[]{id},
                        new BookRowMapper())
                .stream().findAny().orElse(null);
    }

    public Optional<Book> show(String name) {
        return jdbcTemplate.query("SELECT * from Book where name_book=?", new Object[]{name}, new BookRowMapper())
                .stream().findAny();
    }

    public Optional<Book> show(String bookName, String author, int year) {
        return jdbcTemplate.query("SELECT * from Book where name_book=? and author=? and year=?" , new Object[]{bookName, author, year},
                        new BookRowMapper())
                .stream().findAny();
    }

    public void save(Book book) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("book")
                .usingGeneratedKeyColumns("book_id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name_book", book.getNameBook());
        parameters.put("author", book.getAuthor());
        parameters.put("year", book.getYear());

        Number generatedId = simpleJdbcInsert.executeAndReturnKey(parameters);

        book.setBookId(generatedId.longValue());
    }

    public void update(long id, Book updateBook) {
        jdbcTemplate.update("UPDATE Book SET name_book=?, author=?, year=? where book_id=?",
                updateBook.getNameBook(), updateBook.getAuthor(), updateBook.getYear(), id);
    }

    public void releaseTheBookFromThePerson(Long bookId) {
        jdbcTemplate.update("UPDATE Book SET person_id=? where book_id=?",
                null, bookId);
    }


    public void delete(int id) {
        jdbcTemplate.update("DELETE from Book where book_id=?", id);
    }

    public void setPersonId(Long bookId, Long personId) {
        jdbcTemplate.update("UPDATE Book SET person_id=? where book_id=?", personId, bookId);
    }

}

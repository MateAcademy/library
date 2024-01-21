package ua.klunniy.spring.dao;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ua.klunniy.spring.models.Book;
import ua.klunniy.spring.models.BookRowMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
        return jdbcTemplate.query("SELECT * from Book", new BeanPropertyRowMapper<>(Book.class));
    }

    public Book show(int id) {
        return jdbcTemplate.query("SELECT * from Book where id=?", new Object[]{id},
                        new BookRowMapper())
                .stream().findAny().orElse(null);
    }

    public Optional<Book> show(String name) {
        return jdbcTemplate.query("SELECT * from Book where name=?", new Object[]{name}, new BookRowMapper())
                .stream().findAny();
    }

    public void save(Book book) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("book")
                .usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", book.getName());
        parameters.put("description", book.getDescription());

        Number generatedId = simpleJdbcInsert.executeAndReturnKey(parameters);

        book.setId(generatedId.intValue());
    }

    public void update(int id, Book updateBook) {
        jdbcTemplate.update("UPDATE Book SET name=?, description=? where id=?",
                updateBook.getName(), updateBook.getDescription(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE from Book where id=?", id);
    }

}

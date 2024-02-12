package ua.klunniy.spring.dao.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import ua.klunniy.spring.models.Book;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Serhii Klunniy
 */
public class BookRowMapper implements RowMapper<Book> {
    @Override
    public Book mapRow(ResultSet resultSet, int i) throws SQLException {

        Long person_id= resultSet.getLong("person_id");

        Book book = new Book(
                resultSet.getLong("book_id"),
                resultSet.getLong("person_id"),
                resultSet.getString("name_book"),
                resultSet.getString("author"),
                resultSet.getInt("year")
        );

        if (person_id == 0) {
            book.setPersonId(null);
        }
        
        return book;
    }

}

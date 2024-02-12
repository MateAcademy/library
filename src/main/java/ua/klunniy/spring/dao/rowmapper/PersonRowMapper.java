package ua.klunniy.spring.dao.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import ua.klunniy.spring.models.Person;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Serhii Klunniy
 */
public class PersonRowMapper implements RowMapper<Person> {
    @Override
    public Person mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Person(
                resultSet.getLong("person_id"),
                resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                resultSet.getString("patronymic"),
                resultSet.getInt("age"),
                resultSet.getString("email"),
                resultSet.getString("address")
        );
    }

}

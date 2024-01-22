package ua.klunniy.spring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ua.klunniy.spring.models.Person;
import ua.klunniy.spring.models.PersonRowMapper;

import java.sql.*;
import java.util.*;

/**
 * @author Serhii Klunniy
 */
@Component
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    private static final String URL = "jdbc:postgresql://localhost:5432/zero_db";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "test";

    private static final Connection connection;

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query("select * from person", new PersonRowMapper());
    }

    public Person show(long id) {
        return jdbcTemplate.query("select * from person where person_id=?",
                new Object[]{id}, new PersonRowMapper()).stream().findAny().orElse(null);
    }

    public Optional<Person> showByEmail(String email) {
        return jdbcTemplate.query("SELECT * from Person where email=?", new Object[]{email},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }

    public Optional<Person> showByAddress(String address) {
        return jdbcTemplate.query("SELECT * from Person where address=?", new Object[]{address},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }

    public void save(Person person) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("person")
                .usingGeneratedKeyColumns("person_id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("first_name", person.getFirstName());
        parameters.put("last_name", person.getLastName());
        parameters.put("patronymic", person.getPatronymic());
        parameters.put("age", person.getAge());
        parameters.put("email", person.getEmail());
        parameters.put("address", person.getAddress());


        Number generatedId = simpleJdbcInsert.executeAndReturnKey(parameters);

        person.setId(generatedId.longValue());
    }

//    public void save(Person person) {
//        final String sql = "INSERT INTO PERSON (name, surname, age, email) values (?, ?, ?, ?) RETURNING id";
//        try (Connection connection = dbConnector.getConnection();
//             PreparedStatement ps = connection.prepareStatement(sql)) {
//            ps.setString(1, person.getName());
//            ps.setString(2, person.getSurname());
//            ps.setInt(3, person.getAge());
//            ps.setString(4, person.getEmail());
//
//            ResultSet resultSet = ps.executeQuery();
//            if (resultSet.next()) {
//                person.setId(resultSet.getInt("id"));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    public void update(int id, Person updatePerson) {
//        String SQL = "UPDATE Person SET name=?, surname=?, age=?, email=?, address=? where id=?";
//        try (PreparedStatement ps = connection.prepareStatement(SQL)) {
//            ps.setString(1, updatePerson.getName());
//            ps.setString(2, updatePerson.getSurname());
//            ps.setInt(3, updatePerson.getAge());
//            ps.setString(4, updatePerson.getEmail());
//            ps.setString(5, updatePerson.getAddress());
//            ps.setInt(6, id);
//            ps.executeUpdate();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
    }

    public void delete(int id) {
        String SQL = "DELETE  from Person where id=?";
        try (PreparedStatement ps = connection.prepareStatement(SQL)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}

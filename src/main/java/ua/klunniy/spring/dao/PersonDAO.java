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

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query("select * from person order by person_id", new PersonRowMapper());
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

    public void update(int id, Person updatePerson) {
        jdbcTemplate.update("UPDATE Person SET first_name=?, last_name=?, patronymic=?, age=?, email=?, address=? " +
                        "where person_id=?",
                updatePerson.getFirstName(), updatePerson.getLastName(), updatePerson.getPatronymic(),
                updatePerson.getAge(), updatePerson.getEmail(), updatePerson.getAddress(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE from Person where person_id=?", id);
    }

}

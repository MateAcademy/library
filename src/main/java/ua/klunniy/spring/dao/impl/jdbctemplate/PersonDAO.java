package ua.klunniy.spring.dao.impl.jdbctemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ua.klunniy.spring.models.Person;
import ua.klunniy.spring.dao.rowmapper.PersonRowMapper;

import java.sql.PreparedStatement;
import java.sql.SQLException;
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

    public Person show(String firstName, String lastName, String patronymic) {
        return jdbcTemplate.query("select * from person where first_name=? and last_name=? and patronymic=?",
                new Object[]{firstName, lastName, patronymic}, new PersonRowMapper()).stream().findAny().orElse(null);
    }

    public Optional<Person> showByEmail(String email) {
        return jdbcTemplate.query("SELECT * from Person where email=?", new Object[]{email},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }

    public Optional<Person> showByAddress(String address) {
        return jdbcTemplate.query("SELECT * from Person where address=?", new Object[]{address},
                new PersonRowMapper()).stream().findAny();
    }

    public Optional<Person> showByFirstName(String firstName) {
        return jdbcTemplate.query("SELECT * from Person where first_name=?", new Object[]{firstName},
                new PersonRowMapper()).stream().findAny();
    }

    public Optional<Person> showByLastName(String lastName) {
        return jdbcTemplate.query("SELECT * from Person where last_name=?", new Object[]{lastName},
                new PersonRowMapper()).stream().findAny();
    }

    public Optional<Person> showByPatronymic(String patronymic) {
        return jdbcTemplate.query("SELECT * from Person where patronymic=?", new Object[]{patronymic},
                new PersonRowMapper()).stream().findAny();
    }

    public void save(Person person) {
//  jdbcTemplate.update("insert into Person values(1, ?, ?, ?)", person.getName(), person.getAge(),
//                new Person;
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

    public Optional<Person> showSuchPerson(Person person) {
        return jdbcTemplate.query("SELECT * from Person where first_name=? and " +
                "last_name=? and patronymic=?", new Object[]{person.getFirstName(), person.getLastName(),
                person.getPatronymic()}, new PersonRowMapper()).stream().findAny();
    }

    public void testMultipleUpdate(List<Person> personList) {
        for (int i = 0; i < personList.size(); i++) {
            save(personList.get(i));
        }
    }

    public void testBatchUpdate(List<Person> personList) {
        jdbcTemplate.batchUpdate("insert into person(first_name, last_name, patronymic, age, email, address) VALUES (?, ?, ?, ?, ?, ?)", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, personList.get(i).getFirstName());
                ps.setString(2, personList.get(i).getLastName());
                ps.setString(3, personList.get(i).getPatronymic());
                ps.setInt(4, personList.get(i).getAge());
                ps.setString(5, personList.get(i).getEmail());
                ps.setString(6, personList.get(i).getAddress());
            }

            @Override
            public int getBatchSize() {
                return personList.size();
            }
        });
    }

}

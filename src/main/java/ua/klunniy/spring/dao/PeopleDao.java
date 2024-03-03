package ua.klunniy.spring.dao;

import ua.klunniy.spring.models.Person;

import java.util.List;
import java.util.Optional;

/**
 * @author Serhii Klunniy
 */
public interface PeopleDao {

    List<Person> index();

    Person show(long id);

    Person show(String firstName, String lastName, String patronymic);

    Optional<Person> showByEmail(String email);

    Optional<Person> showByAddress(String address);

//    Optional<Person> showByFirstName(String firstName);
//
//    Optional<Person> showByLastName(String lastName);
//
//    Optional<Person> showByPatronymic(String patronymic);

    void save(Person person);

    void update(long id, Person updatePerson);

    void delete(long id);

    Optional<Person> showSuchPerson(Person person);

    void testMultipleUpdate(List<Person> person);

    void testBatchUpdate(List<Person> personList);

}

package ua.klunniy.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.klunniy.spring.dao.impl.jdbctemplate.PersonDAOJDBCTemplateImpl;
import ua.klunniy.spring.models.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Serhii Klunniy
 */
@Service
public class PersonService {
    private final PersonDAOJDBCTemplateImpl personDAOJDBCTemplateImpl;

    @Autowired
    public PersonService(PersonDAOJDBCTemplateImpl personDAOJDBCTemplateImpl) {
        this.personDAOJDBCTemplateImpl = personDAOJDBCTemplateImpl;
    }

    public List<Person> index() {
        return personDAOJDBCTemplateImpl.index();
    }

    public Person show(Long id) {
        if (id == null) {
            return null;
        }
        return personDAOJDBCTemplateImpl.show(id);
    }

    public boolean show(String firstName, String lastName, String patronymic) {
        Person show = personDAOJDBCTemplateImpl.show(firstName, lastName, patronymic);
        return show != null;
    }

    public Optional<Person> showByEmail(String email) {
        return personDAOJDBCTemplateImpl.showByEmail(email);
    }

    public Optional<Person> showByAddress(String address) {
        return personDAOJDBCTemplateImpl.showByAddress(address);
    }

    public Optional<Person> showByFirstName(String firstName) {
        return personDAOJDBCTemplateImpl.showByFirstName(firstName);
    }

    public Optional<Person> showByLastName(String lastName) {
        return personDAOJDBCTemplateImpl.showByLastName(lastName);
    }


    public Optional<Person> showByPatronymic(String patronymic) {
        return personDAOJDBCTemplateImpl.showByPatronymic(patronymic);
    }

    public void save(Person person) {
        if (person != null) {
            personDAOJDBCTemplateImpl.save(person);
        }
    }

    public void update(int id, Person person) {
        if (person != null) {
            personDAOJDBCTemplateImpl.update(id, person);
        }
    }

    public void delete(int id) {
        personDAOJDBCTemplateImpl.delete(id);
    }

    public Optional<Person> showSuchPerson(Person person) {
        return personDAOJDBCTemplateImpl.showSuchPerson(person);
    }

    public void testMultipleUpdate() {
        long before = System.currentTimeMillis();
        personDAOJDBCTemplateImpl.testMultipleUpdate(get100PersonList());
        long after = System.currentTimeMillis();
        System.out.println("Time multiple update Database: " + (after - before));
    }

    public void testBatchUpdate() {
        long before = System.currentTimeMillis();
        personDAOJDBCTemplateImpl.testBatchUpdate(get100PersonList2());
        long after = System.currentTimeMillis();
        System.out.println("Time Batch update Database: " + (after - before));
    }

    private List<Person> get100PersonList() {
        List<Person> personList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Person person = new Person("firstName" + i,
                    "lastName" + i, "patronymic" + i,
                    20, "people" + i + "@.gmail.com", "Ukraine, Kiev, 123456");
            personList.add(person);
        }
        return personList;
    }

    private List<Person> get100PersonList2() {
        List<Person> personList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Person person = new Person("firstName2" + i,
                    "lastName26" + i, "patronymic26" + i,
                    20, "people26" + i + "@gmail.com", "Ukraine26, Kiev, 123456");
            personList.add(person);
        }
        return personList;
    }

}

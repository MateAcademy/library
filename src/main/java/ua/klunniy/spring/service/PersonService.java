package ua.klunniy.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.klunniy.spring.dao.PersonDAO;
import ua.klunniy.spring.models.Person;

import java.util.List;
import java.util.Optional;

/**
 * @author Serhii Klunniy
 */
@Service
public class PersonService {
    private final PersonDAO personDAO;

    @Autowired
    public PersonService(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    public List<Person> index() {
        return personDAO.index();
    }

    public Person show(Long id) {
        if (id == null) {
            return null;
        }
        return personDAO.show(id);
    }

    public Optional<Person> showByEmail(String email) {
        return personDAO.showByEmail(email);
    }

    public Optional<Person> showByAddress(String address) {
        return personDAO.showByAddress(address);
    }

    public void save(Person person) {
        if (person != null) {
            personDAO.save(person);
        }
    }

    public void update(int id, Person person) {
        if (person != null) {
            personDAO.update(id, person);
        }
    }

    public void delete(int id) {
        personDAO.delete(id);
    }

}

package ua.klunniy.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
//import javax.transaction.Transactional;
import ua.klunniy.spring.dao.PeopleDao;
import ua.klunniy.spring.dao.impl.jdbctemplate.PeopleDAOJDBCTemplateImpl;
import ua.klunniy.spring.models.Person;
import ua.klunniy.spring.repositories.PeopleRepository;
//import ua.klunniy.spring.repositories.PeopleRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Serhii Klunniy
 */
@Service
@Transactional(readOnly = true)
public class PeopleService {

    //В один сервисный класс может быть внедрено несколько Дао репозиториев
    private final PeopleDao personDao;
    private final PeopleRepository peopleRepository;
    private final PeopleDAOJDBCTemplateImpl personDAOJDBCTemplateImpl;

    @Autowired
    public PeopleService(PeopleDAOJDBCTemplateImpl personDAOJDBCTemplateImpl,
                         @Qualifier("peopleDaoHibernate") PeopleDao personDao,
                         PeopleRepository peopleRepository) {
        this.personDAOJDBCTemplateImpl = personDAOJDBCTemplateImpl;
        this.personDao = personDao;
        this.peopleRepository = peopleRepository;
    }

    public List<Person> index() {
       // return personDao.index();
        return peopleRepository.findAll();
    }

    public Person show(Long id) {
        if (id == null) {
            return null;
        }
       // return personDao.show(id);
        Optional<Person> byId = peopleRepository.findById(id);
        return byId.orElse(null);
    }

    private List<Person> findByName(String name) {
        return peopleRepository.findByFirstName(name);
    }

    public boolean show(String firstName, String lastName, String patronymic) {
        Person show = personDao.show(firstName, lastName, patronymic);
        return show != null;
    }

    public Optional<Person> showByEmail(String email) {
        return personDao.showByEmail(email);
    }

    public Optional<Person> showByAddress(String address) {
        return personDao.showByAddress(address);
    }

//    public Optional<Person> showByFirstName(String firstName) {
//        return personDao.showByFirstName(firstName);
//    }
//
//    public Optional<Person> showByLastName(String lastName) {
//        return personDao.showByLastName(lastName);
//    }
//
//
//    public Optional<Person> showByPatronymic(String patronymic) {
//        return personDao.showByPatronymic(patronymic);
//    }

    @Transactional
    public void save(Person person) {
        if (person != null) {
            person.setCreatedAt(new Date());
            //personDao.save(person);
           peopleRepository.save(person);
        }
    }

    @Transactional
    public void update(long id, Person person) {
        if (person != null) {
            //personDao.update(id, person);
            person.setId(id);
            peopleRepository.save(person);
        }
    }

    @Transactional
    public void delete(long id) {
        //personDao.delete(id);
        peopleRepository.deleteById(id);
    }

    public Optional<Person> showSuchPerson(Person person) {
        return personDao.showSuchPerson(person);
    }

    public void testMultipleUpdate() {
        long before = System.currentTimeMillis();
        personDao.testMultipleUpdate(get100PersonList());
        long after = System.currentTimeMillis();
        System.out.println("Time multiple update Database: " + (after - before));
    }

    public void testBatchUpdate() {
        long before = System.currentTimeMillis();
        personDao.testBatchUpdate(get100PersonList2());
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

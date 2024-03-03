package ua.klunniy.spring.dao.impl.hibernate;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ua.klunniy.spring.dao.PeopleDao;
import ua.klunniy.spring.models.Person;

import java.util.List;
import java.util.Optional;

/**
 * @author Serhii Klunniy
 */
@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PeopleDaoHibernate implements PeopleDao {

    final SessionFactory sessionFactory;

    @Autowired
    public PeopleDaoHibernate(@Qualifier("hibernateSessionFactory") SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true)
    public List<Person> index() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Person", Person.class).getResultList();
    }

    @Transactional(readOnly = true)
    public Person show(long id) {
        Session session = sessionFactory.getCurrentSession();
//        String hql = "FROM Book WHERE bookId = :book_id";
//        Query query = session.createQuery(hql);
//        query.setParameter("book_id", myBookIdValue);
//        Book book = (Book) query.uniqueResult();
        return session.get(Person.class, id);
    }

    @Transactional(readOnly = true)
    public Person show(String firstName, String lastName, String patronymic) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM Person WHERE firstName = :firstName AND lastName = :lastName AND patronymic = :patronymic";
        Query query = session.createQuery(hql);
        query.setParameter("firstName", firstName);
        query.setParameter("lastName", lastName);
        query.setParameter("patronymic", patronymic);

        Person person = (Person) query.uniqueResult();
        return person;
    }

    @Transactional(readOnly = true)
    public Optional<Person> showByEmail(String email) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "SELECT p from Person p where email=:email";
        Query query = session.createQuery(hql);
        query.setParameter("email", email);
        Person person = (Person) query.uniqueResult();
        Optional<Person> personOptional = Optional.ofNullable(person);
        return personOptional;

    }

    @Transactional(readOnly = true)
    public Optional<Person> showByAddress(String address) {
//        return jdbcTemplate.query("SELECT * from Person where address=?", new Object[]{address},
//                new PersonRowMapper()).stream().findAny();
        Session session = sessionFactory.getCurrentSession();
        String hql = "SELECT p from Person p where address=:address";
        Query query = session.createQuery(hql);
        query.setParameter("address", address);
        Person person = (Person) query.uniqueResult();
        return Optional.ofNullable(person);
    }

//    @Transactional(readOnly = true)
//    public Optional<Person> showByFirstName(String firstName) {
//        return jdbcTemplate.query("SELECT * from Person where first_name=?", new Object[]{firstName},
//                new PersonRowMapper()).stream().findAny();
//    }
//
//    @Transactional(readOnly = true)
//    public Optional<Person> showByLastName(String lastName) {
//        return jdbcTemplate.query("SELECT * from Person where last_name=?", new Object[]{lastName},
//                new PersonRowMapper()).stream().findAny();
//    }
//
//    @Transactional(readOnly = true)
//    public Optional<Person> showByPatronymic(String patronymic) {
//        return jdbcTemplate.query("SELECT * from Person where patronymic=?", new Object[]{patronymic},
//                new PersonRowMapper()).stream().findAny();
//    }

    @Override
    @Transactional
    public void save(Person person) {
        Session session = sessionFactory.getCurrentSession();
        session.save(person);
    }

    @Transactional
    public void update(long id, Person updatePerson) {
        Session session = sessionFactory.getCurrentSession();
        Person person = session.get(Person.class, id);
        person.setFirstName(updatePerson.getFirstName());
        person.setLastName(updatePerson.getLastName());
        person.setPatronymic(updatePerson.getPatronymic());
        person.setAge(updatePerson.getAge());
        person.setEmail(updatePerson.getEmail());
    }

    @Transactional
    public void delete(long id) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(session.get(Person.class, id));
    }

    @Override
    public Optional<Person> showSuchPerson(Person person) {
        Session session = sessionFactory.getCurrentSession();
        Person getPersonFromDB = session.get(Person.class, person.getId());
        return Optional.ofNullable(getPersonFromDB);
    }

    @Override
    public void testMultipleUpdate(List<Person> personL) {

    }

    public void testBatchUpdate(List<Person> personList) {
//        jdbcTemplate.batchUpdate("insert into person(first_name, last_name, patronymic, age, email, address) VALUES (?, ?, ?, ?, ?, ?)", new BatchPreparedStatementSetter() {
//            @Override
//            public void setValues(PreparedStatement ps, int i) throws SQLException {
//                ps.setString(1, personList.get(i).getFirstName());
//                ps.setString(2, personList.get(i).getLastName());
//                ps.setString(3, personList.get(i).getPatronymic());
//                ps.setInt(4, personList.get(i).getAge());
//                ps.setString(5, personList.get(i).getEmail());
//                ps.setString(6, personList.get(i).getAddress());
//            }
//
//            @Override
//            public int getBatchSize() {
//                return personList.size();
//            }
//        });
    }


}

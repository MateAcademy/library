package ua.klunniy.spring;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ua.klunniy.spring.models.Person;

import java.util.List;

/**
 * @author Serhii Klunniy
 */
public class Main {
    public static void main(String[] args) {
        Configuration configuration = new Configuration().addAnnotatedClass(Person.class);
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();

//       session.save(new Person("Serhii", "Klunniy", "Alexandrovich", 67, "d@f.com", "U" ));
//       Person person = session.get(Person.class, 2);
//       person.setFirstName("Anna2");
//       System.out.println(person.getFirstName());
//       System.out.println(person.getLastName());

//        List fromPerson = session.createQuery("from Person where firstName like 'S%'").getResultList();
//        session.createQuery("update Person set firstName = 'II' where age < 6").executeUpdate();
        session.createQuery("delete Person where age < 5").executeUpdate();

        session.getTransaction().commit();


        sessionFactory.close();
    }

}

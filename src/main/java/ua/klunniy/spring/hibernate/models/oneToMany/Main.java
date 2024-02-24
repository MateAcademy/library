package ua.klunniy.spring.hibernate.models.oneToMany;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ua.klunniy.spring.hibernate.models.oneToMany.model.Item;
import ua.klunniy.spring.hibernate.models.oneToMany.model.Person;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Serhii Klunniy
 */
public class Main {
    public static void main(String[] args) {
        Configuration configuration = new Configuration()
                .addAnnotatedClass(Item.class)
                .addAnnotatedClass(Person.class);

        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        try (sessionFactory) {
            session.beginTransaction();

            Person person = new Person("VVVr13", 33);
            Item item = new Item("VVVr13");
            person.addItem(item);

            session.persist(person);

            session.getTransaction().commit();

        }
    }

}

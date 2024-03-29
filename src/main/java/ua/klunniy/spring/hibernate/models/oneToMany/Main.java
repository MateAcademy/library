package ua.klunniy.spring.hibernate.models.oneToMany;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ua.klunniy.spring.hibernate.models.oneToMany.model.Item;
import ua.klunniy.spring.hibernate.models.oneToMany.model.Person;

import java.util.ArrayList;
import java.util.Collections;

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

            Person newPerson = new Person("RR", 80);
            Item newItem = new Item("Item from Hibernate 80", newPerson);

            newPerson.setItems(new ArrayList<>(Collections.singletonList(newItem)));

            session.save(newPerson);
            session.save(newItem);

            session.getTransaction().commit();
        }
    }

}


//Это мы берем человека и выводим на экран
//Person person = session.get(Person.class, 1);
//System.out.println(person.getName());
//List<Item> items = person.getItems();
//System.out.println(items);

//Это мы берем товар и выводим имя человека которому он принадлежит
//Item item = session.get(Item.class, 2);
//System.out.println(item.getName());
//System.out.println(item.getOwner().getName());


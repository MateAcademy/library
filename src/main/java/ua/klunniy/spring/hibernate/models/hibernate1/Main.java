package ua.klunniy.spring.hibernate.models.hibernate1;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * @author Serhii Klunniy
 */
public class Main {
    public static void main(String[] args) {
        Configuration configuration = new Configuration()
                .addAnnotatedClass(Item.class);

        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        Item item = new Item("tv-test");
        try {
            session.beginTransaction();
            session.save(item);
            session.getTransaction().commit();
        } finally {
            sessionFactory.close();
        }
    }
}

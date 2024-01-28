package ua.klunniy.spring.models.hibernate2;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * @author Serhii Klunniy
 */
public class Main {
    public static void main(String[] args) {
        Configuration configuration = new Configuration()
                .addAnnotatedClass(Buyers.class)
                .addAnnotatedClass(Purchase.class);

        SessionFactory sessionFactory = configuration.buildSessionFactory();

//session - это обьект для взаимодействия с хибернейт
        try (Session session = sessionFactory.getCurrentSession()) {

            Buyers buyers = new Buyers("Katti", 40);

//транзакция помогает поддерживать согласованность данных в таблице
            session.beginTransaction();

            session.save(buyers);
            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

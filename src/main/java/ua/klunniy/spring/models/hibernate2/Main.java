package ua.klunniy.spring.models.hibernate2;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

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



//транзакция помогает поддерживать согласованность данных в таблице
            session.beginTransaction();

            Buyers buyers = session.get(Buyers.class, 1);
            List<Purchase> purchaseList = buyers.getPurchaseList();
            purchaseList.stream().forEach( x -> x.setBuyers(null));

            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

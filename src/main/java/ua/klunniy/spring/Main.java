package ua.klunniy.spring;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ua.klunniy.spring.models.Person;
import ua.klunniy.spring.models.hinernate.Item;
import ua.klunniy.spring.models.hinernate.Students;
import ua.klunniy.spring.util.DbConnector;
import ua.klunniy.spring.util.DbConnector1;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Serhii Klunniy
 */
public class Main {

    public static void main(String[] args) throws SQLException {
        DbConnector1 dbConnector = new DbConnector1();
        Connection connection = dbConnector.getConnection();

        ResultSet resultSet = connection.createStatement().executeQuery("select * from book");



    }
//    public static void main(String[] args) {
//        Configuration configuration = new Configuration()
//                .addAnnotatedClass(Item.class)
//                .addAnnotatedClass(Students.class);
//
//        SessionFactory sessionFactory = configuration.buildSessionFactory();
//        Session session = sessionFactory.getCurrentSession();
//
//        session.beginTransaction();
//
//       Students student1 = new Students("Test1");
//       Students student2 = new Students("Test2");
//       Students student3 = new Students("Test3");
//
//       session.save(student1);
//       session.save(student2);
//       session.save(student3);
//
//        session.getTransaction().commit();
//        sessionFactory.close();
//
////      session.save(new Person("Serhii", "Klunniy", "Alexandrovich", 67, "d@f.com", "U" ));
////      Person person = session.get(Person.class, 2);
////      person.setFirstName("Anna2");
////      System.out.println(person.getFirstName());
////      System.out.println(person.getLastName());
//
////      List fromPerson = session.createQuery("from Person where firstName like 'S%'").getResultList();
////      session.createQuery("update Person set firstName = 'II' where age < 6").executeUpdate();
////      session.createQuery("delete Person where age < 5").executeUpdate();
//
//    }

}

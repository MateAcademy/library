package ua.klunniy.spring;

import ua.klunniy.spring.models.Book;
import ua.klunniy.spring.util.DbConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Serhii Klunniy
 */
public class MainForJDBC {

    public static void main(String[] args) {
//        List<Book> bookList = index();
//        bookList.forEach(System.out::println);

    }

    private static List<Book> index() {
        List<Book> bookList = new ArrayList<>();
        DbConnector dbConnector = new DbConnector();
        String sql = "SELECT * FROM book";
        try (Connection connection = dbConnector.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
//Statement, это тот объект, который содержит в себе SQL запрос:
            while (resultSet.next()) {
                Book book = new Book();
                book.setBookId(resultSet.getLong("book_id"));
                book.setPersonId((resultSet.getLong("person_id")));
                book.setNameBook((resultSet.getString("name_book")));
                book.setNameBook((resultSet.getString("author")));
                book.setYear((resultSet.getInt("year")));
                bookList.add(book);
            }
        } catch (Exception e) {
            System.out.println("Error " + e);
        }
        return bookList;
    }

    private static void save(Book book) {
        DbConnector dbConnector = new DbConnector();
        String sql = "insert into book(person_id, name_book, author, year) VALUES (?, ?, ?, ?)";
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setLong(1, book.getPersonId());
            statement.setString(2, book.getNameBook());
            statement.setString(3, book.getAuthor());
            statement.setLong(4, book.getYear());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
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



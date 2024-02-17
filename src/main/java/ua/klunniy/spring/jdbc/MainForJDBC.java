package ua.klunniy.spring.jdbc;

import ua.klunniy.spring.models.Book;
import ua.klunniy.spring.util.db.DbConnector;

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

//        Book book = new Book("test3", "test3", 1901);
//        save(book);

//        Book book = show(1L);
//        System.out.println(book);

//        Book book = new Book("111", "111", 1981);
//        update(2, book);

        delete(4l);
    }

    private static List<Book> index() {
        List<Book> bookList = new ArrayList<>();
        DbConnector dbConnector = new DbConnector();
        String sql = "SELECT * FROM book";
        try (Connection connection = dbConnector.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
//Statement, это тот объект, который содержит в себе SQL запрос:
            if (resultSet.next()) {
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
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setObject(1, book.getPersonId());
            ps.setString(2, book.getNameBook());
            ps.setString(3, book.getAuthor());
            ps.setLong(4, book.getYear());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static Book show(Long id) {
        DbConnector dbConnector = new DbConnector();
        String sql = "select * from book where book_id = ?";
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return new Book(
                        resultSet.getLong("book_id"),
                        resultSet.getLong("person_id"),
                        resultSet.getString("name_book"),
                        resultSet.getString("author"),
                        resultSet.getInt("year")
                );
            }
        } catch (Exception e) {
            System.out.println("Error in method show, id = " + id + ", exception= " + e);
        }
        return null;
    }

    private static void update(int id, Book bookUpdate) {
        DbConnector dbConnector = new DbConnector();
        String sql = "update book set person_id=?, name_book=?, author=?, year=? where book_id = ?";
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setObject(1, bookUpdate.getPersonId());
            ps.setString(2, bookUpdate.getNameBook());
            ps.setString(3, bookUpdate.getAuthor());
            ps.setLong(4, bookUpdate.getYear());
            ps.setLong(5, id);
//не подходит так как запрос не вернул результатов
            //           ps.executeQuery();
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    private static void delete (Long id) {
        DbConnector dbConnector = new DbConnector();
        String sql = "delete from book where book_id = ?";
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setObject(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
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



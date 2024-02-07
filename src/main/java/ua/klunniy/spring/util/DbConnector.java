package ua.klunniy.spring.util;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DbConnector {
//    static final Logger logger = Logger.getLogger(DbConnector.class);

    @Value("${person.DbURL}")
    String DbURL = "jdbc:postgresql://localhost:5432/ava_db";

    @Value("${person.LOGIN}")
    String LOGIN = "password";

    @Value("${person.PASSWORD}")
    String PASSWORD = "test";

    public DbConnector() {
    }

    public Connection getConnection() {
        try {
//          System.setProperty("jdbc.driver", "org.postgresql.Driver");
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(DbURL, LOGIN, PASSWORD);
//            logger.debug("connection" + connection);
            return connection;
        } catch (SQLException | ClassNotFoundException e) {
//            logger.error("no connect to db: " + e);
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostConstruct
    private void initMethod() {
        Connection connection = getConnection();
        if (connection != null) {
            System.out.println("Doing my initialization");
        } else {
            System.out.println("Doing my initialization ERROR with connection");
        }
    }

    @PreDestroy
    private void doMyDestroy() {
        System.out.println("Doing my destruction");
    }

}

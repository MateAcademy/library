package ua.klunniy.spring.util.db;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
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
    private static final String URL = "jdbc:postgresql://localhost:5432/ava_db";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "postgres";

    private Connection connection;

    public Connection getConnection() {
        try {
//          System.setProperty("jdbc.driver", "org.postgresql.Driver");
            Class.forName("org.postgresql.Driver");
//            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
//            logger.debug("connection" + connection);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
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
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("connection close");
            throw new RuntimeException(e);
        }
        System.out.println("Doing my destruction");
    }

}

package ua.klunniy.spring.util;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DbConnector {
//    static final Logger logger = Logger.getLogger(DbConnector.class);

    @Value("${person.DbURL}")
    String DbURL;

    @Value("${person.LOGIN}")
    String LOGIN;

    @Value("${person.PASSWORD}")
    String PASSWORD;

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

}

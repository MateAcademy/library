package ua.klunniy.spring.util;

import org.springframework.beans.factory.annotation.Value;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Serhii Klunniy
 */
public class DbConnector1 {
    String DbURL = "jdbc:postgresql://localhost:5432/ava_db";

    String LOGIN = "postgres";

    String PASSWORD = "test";

    public DbConnector1() {
    }

    public Connection getConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(DbURL, LOGIN, PASSWORD);
            return connection;
        } catch (SQLException | ClassNotFoundException e) {
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

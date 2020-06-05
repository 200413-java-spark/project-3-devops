package devops.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DatabaseUtil {

    private static final String URL = AppProperties.PROPERTIES.getProperty("DATABASE_URL");
    private static final String USER = AppProperties.PROPERTIES.getProperty("DATABASE_USER");
    private static final String PASSWORD = AppProperties.PROPERTIES.getProperty("DATABASE_PASSWORD");

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
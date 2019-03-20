package configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
    public Connection initConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/course_manager";
        String user = "app_user";
        String password = "sda";
        return DriverManager.getConnection(url, user, password);
    }
}

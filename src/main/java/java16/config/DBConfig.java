package java16.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConfig {
    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/employees_task",
                    "postgres",
                    "baiel123"
            );
            System.out.println("Connection established");
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return conn;
    }
}

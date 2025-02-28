package org.ruxlsr.dataaccess.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlDbConnection {
    private static MysqlDbConnection instance;
    private final Connection connection;
    String URL = "jdbc:mysql://localhost:3306/gradeManager";
    String USER = "root";
    String PASSWORD = "root";

    private MysqlDbConnection() throws SQLException {
        this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static MysqlDbConnection getInstance() throws SQLException {
        if (instance == null || instance.connection.isClosed()) {
            instance = new MysqlDbConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}

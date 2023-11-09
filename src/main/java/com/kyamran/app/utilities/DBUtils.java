package com.kyamran.app.utilities;

import lombok.Getter;

import java.sql.*;

@Getter
public class DBUtils {
    private static DBUtils instance;
    private static Connection connection;

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost/test";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    public DBUtils() {
    }

    public static void getInstance() {
        if (instance == null) {
            instance = new DBUtils();
        }
    }

    public static Connection getConnection() throws SQLException {
        openConnection();
        return connection;
    }

    public static void openConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
    }

    public static void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    public static PreparedStatement getPreparedStatementWithKeys(String sql) throws SQLException {
        return getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
    }
}

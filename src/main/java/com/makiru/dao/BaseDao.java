package com.makiru.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class BaseDao {
    private static String driver;
    private static String url;
    private static String username;
    private static String password;

    static {
        InputStream inputStream = BaseDao.class.getClassLoader().getResourceAsStream("config.properties");
        Properties properties = new Properties();
        try {
            properties.load(inputStream);
            driver = properties.getProperty("driver");
            url = properties.getProperty("url");
            username = properties.getProperty("username");
            password = properties.getProperty("password");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        Connection connection = DriverManager.getConnection(url, username, password);
        return connection;
    }

    public static ResultSet execute(PreparedStatement statement, ResultSet resultSet, Object[] params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            statement.setObject(i + 1, params[i]);
        }
        resultSet = statement.executeQuery();
        return resultSet;
    }

    public static int execute(PreparedStatement statement, Object[] params) throws SQLException {
        int updateRow = 0;
        for (int i = 0; i < params.length; i++) {
            statement.setObject(i + 1, params[i]);
        }
        updateRow = statement.executeUpdate();
        return updateRow;
    }

    public static boolean closeResources(Connection connection, PreparedStatement statement, ResultSet resultSet){
        boolean tag = true;
        if(resultSet != null){
            try {
                resultSet.close();
                resultSet = null;
            } catch (SQLException e) {
                tag = false;
                e.printStackTrace();
            }
        }
        if(statement != null){
            try {
                statement.close();
                statement = null;
            } catch (SQLException e) {
                tag = false;
                e.printStackTrace();
            }
        }
        if(connection != null){
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                tag = false;
                e.printStackTrace();
            }
        }
        return tag;
    }
}

package com.example.poi.utils;

import java.sql.*;
import java.util.Properties;

public class JdbcUtils {
    private static String username;
    private static String password;
    private static String url;
    static{
        //注册驱动
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Properties properties = new Properties();
            properties.load(JdbcUtils.class.getClassLoader().getResourceAsStream("application.properties"));
            username = properties.getProperty("spring.datasource.username");
            password = properties.getProperty("spring.datasource.password");
            url = properties.getProperty("spring.datasource.url");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        try {
            Connection connection = getConnection(username, password, url);
            //获取预处理对象
            String sql = "select * from route";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String id = resultSet.getString("id");
                System.out.println(id);
            }
            closeResource(connection, preparedStatement, resultSet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void closeResource(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) throws SQLException {
        if (resultSet != null) {
            resultSet.close();
        }
        if (preparedStatement != null) {
            preparedStatement.close();
        }
        if (connection != null) {
            connection.close();
        }

    }

    private static Connection getConnection(String username, String password, String url) throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}

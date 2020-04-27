package com.example.poi.utils;

import java.sql.*;
import java.util.LinkedList;
import java.util.Properties;

public class ConnectionPool {
   private static LinkedList<Connection> linkedList = new LinkedList<>();

    public ConnectionPool() throws Exception {
        //初始化连接池连接数
        for (int i = 0; i < 5; i++) {
            Connection connection = DriverManager.getConnection(url, username, password);
            linkedList.add(new MyConnection(connection,linkedList));
        }
    }

    private static String username;
    private static String password;
    private static String url;
    static{
        //注册驱动
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Properties properties = new Properties();
            properties.load(ConnectionPool.class.getClassLoader().getResourceAsStream("application.properties"));
            username = properties.getProperty("spring.datasource.username");
            password = properties.getProperty("spring.datasource.password");
            url = properties.getProperty("spring.datasource.url");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        try {
            new ConnectionPool();
            System.out.println(getSize());
            Connection connection = getConnection(username, password, url);
            Connection connection1 = getConnection(username, password, url);
            Connection connection2 = getConnection(username, password, url);
            Connection connection3 = getConnection(username, password, url);
            Connection connection4 = getConnection(username, password, url);
            Connection connection5 =  getConnection(username, password, url);
            connection.close();
            connection1.close();
            connection2.close();
            connection3.close();
            connection4.close();
            connection5.close();
            System.out.println(getSize());
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
    }

    private static Connection getConnection(String username, String password, String url) throws SQLException {
        if(linkedList.size()>0){
            return linkedList.removeFirst();
        }
        return DriverManager.getConnection(url, username, password);
    }

    private static int getSize(){
        return linkedList.size();
    }
}

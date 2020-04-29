package com.example.poi.utils;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.*;
import java.util.LinkedList;
import java.util.Properties;
import java.util.logging.Logger;

public class ConnectionPool implements DataSource {
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
            ConnectionPool connectionPool = new ConnectionPool();
            System.out.println(getSize());
            Connection connection = connectionPool.getConnection();
            Connection connection1 = connectionPool.getConnection();
            Connection connection2 = connectionPool.getConnection();
            Connection connection3 = connectionPool.getConnection();
            Connection connection4 = connectionPool.getConnection();
            Connection connection5 =  connectionPool.getConnection();
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

   /* private static Connection getConnection(String username, String password, String url) throws SQLException {

    }*/

    private static int getSize(){
        return linkedList.size();
    }

    @Override
    public Connection getConnection() throws SQLException {
        if(linkedList.size()>0){
            return linkedList.removeFirst();
        }
        return DriverManager.getConnection(url, username, password);
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return null;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {

    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {

    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }
}

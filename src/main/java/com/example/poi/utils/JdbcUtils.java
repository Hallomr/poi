package com.example.poi.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class JdbcUtils {


    public static void main(String[] args) {
        try {
            //注册驱动
            Class.forName("com.mysql.cj.jdbc.Driver");


            //创建连接对象
            String username ="root";
            String password ="123";
            String url =  "jdbc:mysql://localhost:3306/poi?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
            Connection connection = DriverManager.getConnection(url, username, password);
            //获取预处理对象
            String sql = "select * from route";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String id = resultSet.getString("id");
                System.out.println(id);
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

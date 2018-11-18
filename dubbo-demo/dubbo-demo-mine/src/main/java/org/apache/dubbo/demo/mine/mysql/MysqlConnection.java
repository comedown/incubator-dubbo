package org.apache.dubbo.demo.mine.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlConnection {

    private final static String URL = "jdbc:mysql://localhost:3306/tebon?useUnicode=true&characterEncoding=utf8&useSSL=true";
    private final static String DRIVER = "com.mysql.jdbc.Driver";
    private final static String USERNAME = "root";
    private final static String PASSWORD = "123456";

    public static void main(String[] args) {
        Connection conn = null;
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

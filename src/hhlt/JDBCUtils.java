package hhlt;
/*

CREATE TABLE Person (
  myid INT PRIMARY KEY NOT NULL AUTO_INCREMENT  COMMENT '学号',
  myname VARCHAR(255) NOT NULL DEFAULT '' COMMENT '名字',
  mypassword VARCHAR(255) NOT NULL DEFAULT '' COMMENT '密码'
);
INSERT INTO Person VALUES (1, '张三', '123456'), (2, '李四', '123'), (3, '王五', '234');

 */

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Properties;

public class JDBCUtils {

/*
    // MySQL 8.0 以上版本 - JDBC 驱动名及数据库 URL
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_NAME = "student";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/" + DB_NAME + "?useSSL=false&serverTimezone=UTC";

    // 数据库的用户名与密码，需要根据自己的设置
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

*/


    // MySQL 8.0 以上版本 - JDBC 驱动名及数据库 URL

    private static String DB_URL = "";
    private static final String DB_URL_END = "?useSSL=false&serverTimezone=UTC";

    // 数据库的用户名与密码，需要根据自己的设置
    private static String USER = "";
    private static String PASSWORD = "";

    //静态代码块,和类一起执行,只加载一次
    static {

        try {
            //创建
            Properties pro = new Properties();
            ///获取src文件路径下的文件的方式---->ClassLoader 类加载器
            ClassLoader classLoader = JDBCUtils.class.getClassLoader();
            URL res = classLoader.getResource("jdbc.properties");

            assert res != null;
            String path = res.getPath();


            //加载文件
            pro.load(new FileReader(path));
            //获取数据,赋值
            String JDBC_DRIVER = pro.getProperty("jdbc_driver");
            DB_URL = pro.getProperty("db_url") + pro.getProperty("db_name") + DB_URL_END;
            USER = pro.getProperty("user");
            PASSWORD = pro.getProperty("pssword");
            Class.forName(JDBC_DRIVER);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }


    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(DB_URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static void close(ResultSet rs, PreparedStatement pstmt, Connection conn) {
        // 关闭rs资源
        try {
            if (rs != null) rs.close();
        } catch (SQLException ignored) {

        }
        // 关闭stmt资源
        try {
            if (pstmt != null) pstmt.close();
        } catch (SQLException ignored) {
        }

        //  关闭conn资源
        try {
            if (conn != null) conn.close();
        } catch (SQLException ignored) {

        }
    }


}













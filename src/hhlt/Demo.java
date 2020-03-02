package hhlt;
//888999
import java.sql.*;

public class Demo {

    // MySQL 8.0 以下版本 - JDBC 驱动名及数据库 URL
    //static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    //static final String DB_URL = "jdbc:mysql://localhost:3306/RUNOOB";

    // MySQL 8.0 以上版本 - JDBC 驱动名及数据库 URL
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_NAME="newtable";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/"+DB_NAME+"?useSSL=false&serverTimezone=UTC";

    // 数据库的用户名与密码，需要根据自己的设置
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // 注册 JDBC 驱动
            Class.forName(JDBC_DRIVER);

            // 打开链接

            conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            System.out.println("连接数据库...");
            // 执行查询

            stmt = conn.createStatement();
            //SQL语句
            String sql="SELECT id, name, url FROM websites";

            rs = stmt.executeQuery(sql);

            // 展开结果集数据库
            //判断游标是否末尾
            while (rs.next()) {
                // 通过字段检索
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String url = rs.getString("url");

                // 输出数据
                System.out.print("ID: " + id);
                System.out.print(", 站点名称: " + name);
                System.out.print(", 站点 URL: " + url);
                System.out.print("\n");
            }


            System.out.println("数据库展示成功");
        } catch (SQLException | ClassNotFoundException se) {
            // 处理 JDBC 错误 // 处理 Class.forName 错误
            se.printStackTrace();
        }
        finally {

            // 关闭rs资源
            try {
                if (rs != null) rs.close();
            } catch (SQLException ignored) {

            }
            // 关闭stmt资源
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException ignored) {
            }

            //  关闭conn资源
            try {
                if (conn != null) conn.close();
            } catch (SQLException ignored) {

            }
        }

    }
}
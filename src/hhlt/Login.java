package hhlt;
/**
 * JDBC控制事务,一个包含多个步骤的业务操作,如果这个操作被事务管理,多个步骤要么同时成功,要么同时失败
 * 详见A ,B,C
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;


public class Login {

    /**
     * 根据用户名字和密码判断是否登录成功
     * @param username
     * @param password
     * @return
     */
    private static boolean isLoginSuccess(String username, String password) {
        if (username == null || password == null) {
            return false;
        }
        ResultSet rs = null;
        Connection conn = null;
        PreparedStatement psmt = null;  //解决SQL注入问题,提高效率 占位符
        try {
            conn = JDBCUtils.getConnection();
            //使用Connection来管理事务
            if(conn==null){
                return false;
            }
            conn.setAutoCommit(false);//A

            String sql = "SELECT * FROM person WHERE myname=? and mypassword=?";
            psmt = conn.prepareStatement(sql);
            //给?复制 1代表第一个参数 2代表第二个参数
            psmt.setString(1, username);
            psmt.setString(2, password);
            rs = psmt.executeQuery();

            conn.commit();//B
            return rs.next();
        } catch (SQLException e) {

            try {
                conn.rollback();//C //回滚
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            JDBCUtils.close(rs, psmt, conn);
        }

    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.println("请输入用户名:");
        String username = sc.nextLine().trim();
        System.out.println("请输入密码:");
        String password = sc.nextLine().trim();
        if (isLoginSuccess(username, password)) {
            System.out.println("登录成功!");
        } else {
            System.out.println("用户名或密码错误!");
        }

    }
}

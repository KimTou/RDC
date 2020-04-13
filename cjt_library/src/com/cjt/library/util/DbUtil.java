package com.cjt.library.util;
import java.sql.*;

public class DbUtil {
    //数据库地址
    private static String dbURL = "jdbc:mysql://localhost:3306/library?useUnicode=true&characterEncoding=utf-8&useSSL=false&zeroDateTimeBehavior=convertToNull";
    //用户名
    private static String dbUser = "root";
    //密码
    private static String dbPassword = "123456";
    //驱动名称
    private static String jdbcName = "com.mysql.jdbc.Driver";

    /**
     * 获取数据库连接
     *
     * @return
     * @throws Exception
     */
    public static Connection getCon() throws SQLException, ClassNotFoundException {
        Class.forName(jdbcName);
        Connection con = DriverManager.getConnection(dbURL, dbUser, dbPassword);
        return con;
    }

    /**
     * 关闭连接
     *
     * @param con
     * @throws Exception
     */
    public static void close(ResultSet rs, PreparedStatement st, Connection con) throws SQLException {
        if (rs != null) {
            rs.close();
            if (st != null) {
                st.close();
                if (con != null) {
                    con.close();
                }
            }
        }
    }
}

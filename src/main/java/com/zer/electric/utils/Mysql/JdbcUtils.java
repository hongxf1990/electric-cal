package com.zer.electric.utils.Mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author zer
 * @create 2016-11-25 17:05
 */
public class JdbcUtils {
    /**
     * 数据库连接地址
     */
    private static String url;
    /**
     * 用户名
     */
    private static String userName;
    /**
     * 密码
     */
    private static String password;

    private static String driver;

    /**
     * 装载驱动
     */
    static {

        DbConfig config = new DbConfig();
        url = config.getUrl();
        userName = config.getUserName();
        password = config.getPassword();
        driver = config.getDriver();

        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    /**
     * 建立数据库连接
     *
     * @return
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        Connection conn;
        conn = DriverManager.getConnection(url, userName, password);
        return conn;
    }

    /**
     * 释放连接
     *
     * @param conn
     */
    private static void freeConnection(Connection conn) {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 释放statement
     *
     * @param statement
     */
    private static void freeStatement(Statement statement) {
        try {
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 释放resultset
     *
     * @param rs
     */
    private static void freeResultSet(ResultSet rs) {
        try {
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 释放资源
     *
     * @param conn
     * @param statement
     * @param rs
     */
    public static void free(Connection conn, Statement statement, ResultSet rs) {
        if (rs != null) {
            freeResultSet(rs);
        }
        if (statement != null) {
            freeStatement(statement);
        }
        if (conn != null) {
            freeConnection(conn);
        }
    }
}

package com.fei.utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.naming.InsufficientResourcesException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DaoUtil {

    private static ComboPooledDataSource dataSource = new ComboPooledDataSource();
    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

    /**
     * 从线程中获取连接
     */
    public static Connection getConnection() throws Exception{
        Connection connection = threadLocal.get();
        if (connection == null)
        {
            connection = dataSource.getConnection();
            threadLocal.set(connection);
        }
        return connection;
    }

    /**
     * 获取数据源
     */
    public static DataSource getDatasource()
    {
        return dataSource;
    }

    // 释放Connection
    public static void closeConnection(Connection connection)
    {
        if (connection != null)
        {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    // 释放statement
    public static void closeStatement(Statement statement)
    {
        if (statement != null)
        {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // 释放结果集
    public static void closeResultSet(ResultSet rs )
    {
        if (rs != null)
        {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    // 释放连接
    public static void closeResource(Statement statement, ResultSet resultSet)
    {
        closeStatement(statement);
        closeResultSet(resultSet);
    }

    // 释放连接
    public static void closeResource(Connection connection, Statement statement, ResultSet
            resultSet)
    {
        closeConnection(connection);
        closeResource(statement, resultSet);
    }

    // 开启事务
    public static void startTransaction() throws Exception{
        getConnection().setAutoCommit(false);
    }

    // 事务提交并且释放资源
    public static void commitAndClose()
    {
        Connection conn = null;
        try{
            conn = getConnection();
            // 事务回滚
            conn.rollback();
            // 关闭资源
            conn.close();
            // 解除绑定
            threadLocal.remove();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

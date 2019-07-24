package com.fei.dao.impl;

import com.fei.dao.IUserDao;
import com.fei.domain.User;
import com.fei.utils.DaoUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;

public class IUserDaoImpl implements IUserDao {

    QueryRunner qr = new QueryRunner(DaoUtil.getDatasource());

    // 添加用户
    @Override
    public void addUser(User user) throws SQLException {
        String sql = "insert into user " +
                "(uid, username, password, name, email, telephone, birthday, sex, state, code)"+
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        qr.update(sql, user.getUid(), user.getUsername(), user.getPassword(), user.getName(),
                user.getEmail(), user.getTelephone(), user.getBirthday(), user.getSex(),
                user.getState(), user.getCode());
    }

    //  通过code查询用户信息
    @Override
    public User getUserByCode(String code) throws Exception {
        String sql = "select * from user where code = ? ";
        return qr.query(sql, new BeanHandler<User>(User.class), code);
    }

    // 更新用户信息
    @Override
    public void updateUserMsg(User user) throws Exception {
        String sql = "update user set " +
                "username = ? ," +
                "password = ? ," +
                "name = ? ," +
                "email = ? ," +
                "telephone = ?," +
                "birthday = ? ," +
                "sex = ? ," +
                "state = ? ," +
                "code = ? " +
                "where uid = ?" ;
        qr.update(sql, user.getUsername(), user.getPassword(), user.getName(), user.getEmail() ,
                         user.getTelephone(), user.getBirthday(), user.getSex(), user.getState() ,
                        user.getCode(), user.getUid());
    }

    @Override
    public User getUserByUsername(String userName) throws Exception {
        String sql = "select * from user where username = ?" ;
        return qr.query(sql, new BeanHandler<User>(User.class), userName);
    }
}

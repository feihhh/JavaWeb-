package com.fei.dao;

import com.fei.domain.User;

import java.sql.SQLException;

public interface IUserDao {

    // 添加用户
    void addUser(User user) throws SQLException;

    // 通过code码查询对应的用户
    User getUserByCode(String code) throws Exception;

    void updateUserMsg(User user)  throws Exception;

    User getUserByUsername(String userName) throws Exception;
}

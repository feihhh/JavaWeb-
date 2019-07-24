package com.fei.service;

import com.fei.domain.User;

public interface IUserService {

    /**
     * 添加用户
     */
    public void addUser(User user) throws Exception;

    User getUserByCode(String code) throws Exception;

    void updateUserMsg(User user) throws Exception;

    User getUserByUsername(String userName) throws Exception;
}

package com.fei.service.impl;

import com.fei.dao.IUserDao;
import com.fei.dao.impl.IUserDaoImpl;
import com.fei.domain.User;
import com.fei.service.IUserService;

public class IUserServiceImpl implements IUserService {

    IUserDao dao = new IUserDaoImpl();


    @Override
    public void addUser(User user) throws Exception {
        dao.addUser(user);
    }

    @Override
    public User getUserByCode(String code) throws Exception {
        return dao.getUserByCode(code);
    }

    @Override
    public void updateUserMsg(User user) throws Exception {
        dao.updateUserMsg(user);
    }

    @Override
    public User getUserByUsername(String userName) throws Exception {
        return dao.getUserByUsername(userName);
    }
}

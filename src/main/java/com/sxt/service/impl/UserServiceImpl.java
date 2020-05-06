package com.sxt.service.impl;

import com.sxt.dao.UserDao;
import com.sxt.service.UserService;

public class UserServiceImpl implements UserService {
    private UserDao userDao ;
    @Override
    public void save() {
        userDao.save();
    }

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}

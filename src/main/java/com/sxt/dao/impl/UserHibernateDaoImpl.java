package com.sxt.dao.impl;

import com.sxt.dao.UserDao;

public class UserHibernateDaoImpl implements UserDao {

    @Override
    public void save() {
        System.out.println("利用hibernate执行了新增....");
    }
}

package com.sxt.dao.impl;

import com.sxt.dao.UserDao;

public class UserJdbcDaoImpl implements UserDao {
    @Override
    public void save() {
        System.out.println("利用jdbc执行了新增...");
    }
}

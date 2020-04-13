package com.cjt.library.service;

import com.cjt.library.bean.Msg;
import com.cjt.library.dao.UserDao;
import com.cjt.library.entity.User;
import com.cjt.library.util.ValidateUtil;

public class UserService {
    private UserDao userDao = new UserDao();

    /**
     * 用户登录
     * @param userName
     * @param password
     * @return
     */
    public Msg login(String userName, String password) {
        User user = new User();
        if (ValidateUtil.isInvalidUserName(userName)) {
            return new Msg("用户名为空", null);
        }
        user.setUserName(userName);
        user.setPassword(password);
        Msg msg = userDao.login(user);
        return msg;
    }

    /**
     * 用户注册
     * @param userName
     * @param password
     * @return
     */
    public Msg register(String userName, String password) {
        User user = new User();
        if (ValidateUtil.isInvalidUserName(userName)) {
            return new Msg("用户名为空", null);
        }
        user.setUserName(userName);
        user.setPassword(password);
        Msg msg = userDao.register(user);
        return msg;
    }
}

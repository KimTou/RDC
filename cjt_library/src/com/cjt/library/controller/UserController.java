package com.cjt.library.controller;

import com.cjt.library.bean.Msg;
import com.cjt.library.service.UserService;

public class UserController {
    private UserService userService = new UserService();

    /**
     * 用户登录
     *
     * @param userName
     * @param password
     * @return
     */
    public Msg login(String userName, String password) {
        return userService.login(userName, password);
    }

    /**
     * 用户注册
     */
    public Msg register(String userName, String password) {
        return userService.register(userName, password);
    }
}
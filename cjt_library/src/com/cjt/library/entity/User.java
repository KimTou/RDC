package com.cjt.library.entity;

public class User {
    private int userId;  //用户id

    private String userName;  //用户名

    private String password;  //密码

    /**
     * 得到用户id
     * @return
     */
    public int getUserId() {
        return userId;
    }

    /**
     * 设置用户id
     * @param userId
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * 得到用户名字
     * @return
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置用户名字
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 得到用户密码
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置用户密码
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {         //重写方法，以便详细输出用户信息
        if (userId != 0) {
            return " 用户id :" + userId + ", 用户名 :" + userName;
        }
        else
            return " 用户名 :" + userName;
    }
}
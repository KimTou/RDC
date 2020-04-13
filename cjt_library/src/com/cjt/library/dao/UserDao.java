package com.cjt.library.dao;

import java.sql.*;

import com.cjt.library.bean.Msg;
import com.cjt.library.entity.User;
import com.cjt.library.util.DbUtil;

public class UserDao {
    private Connection con;
    private PreparedStatement stmt;
    private ResultSet rs;

    /**
     * 用户登录
     * @param user
     * @return
     */
    public Msg login(User user){
        try {
            con = DbUtil.getCon();
            String sql = "SELECT * FROM user WHERE user_name = ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, user.getUserName());
            rs = stmt.executeQuery();
            if(rs.next()){            //若登录成功，则填写用户信息到该用户的对象
                if(user.getPassword().equals(rs.getString("password"))){
                    user.setUserId(rs.getInt("user_id"));
                    user.setUserName(rs.getString("user_name"));
                    return new Msg("登录成功",user);
                }
                return new Msg("密码错误",null);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally{
            try{
                DbUtil.close(rs,stmt, con);
            } catch(SQLException e){
                e.printStackTrace();
            }
        }
        return new Msg("该用户不存在",null);
    }

    /**
     * 用户注册
     * @param user
     * @return
     */
    public Msg register(User user) {
        try {
            con = DbUtil.getCon();
            String sql = "INSERT INTO user (user_name,password) values(?,?)";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, user.getUserName());     //得到用户刚刚填写的信息
            stmt.setString(2, user.getPassword());
            stmt.execute();
            return new Msg("注册成功",user);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return new Msg("注册失败",null);
        } finally{
            try{
                DbUtil.close(rs,stmt, con);
            } catch(SQLException e){
                e.printStackTrace();
            }
        }
    }
}


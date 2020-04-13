package com.cjt.library.util;

public class ValidateUtil {
    //验证用户名
    public static boolean isInvalidUserName(String userName){
        if(userName == null || userName.equals("")){          //判断是否为空
            return true;
        }
        return false;
    }
}

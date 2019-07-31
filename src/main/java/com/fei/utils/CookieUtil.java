package com.fei.utils;

import javax.servlet.http.Cookie;

public class CookieUtil {

    // 获取指定名称的cookie
    public static Cookie getCookie(String cNamem, Cookie[] cookies){
        // 如果cookieswei空，返回空
        if (cookies == null){
            return null;
        }
        for (Cookie c : cookies){
            if (c.getName().equals(cNamem)){
                return c;
            }
        }
        return null;
    }
}

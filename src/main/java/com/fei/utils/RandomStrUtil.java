package com.fei.utils;

import java.util.UUID;

/**
 * 主要功能：生成随机唯一字符串
 */
public class RandomStrUtil {

    // 生成随机字符串 uid
    public static String randUid()
    {
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }

    // 生成随机code
    public static String randCode()
    {
        return randUid();
    }

    public static void main(String[] args) {
        System.out.println(randCode());
        System.out.println(randUid());
    }

}

package com.fei.domain;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Cart {

    // 购物车中的商品总数
    private int totalCount;

    // 用一个map保存购物车中的信息
    private Map<String, CartItems> map;

    // 购物车中的总金额
    private double totalMoney;

    // 购物车中的总积分
    private int totalCredits ;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public Map<String, CartItems> getMap() {
        return map;
    }

    public void setMap(Map<String, CartItems> map) {
        this.map = map;
    }

    public double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney() {
        if (this.map == null || this.map.size() == 0) {
            this.totalMoney = 0;
        }
        else {
            int sum = 0;
            Set<String> strings = map.keySet();
            for (String str : strings){
                sum += map.get(str).getSubtotal();
            }
            this.totalMoney = sum;
        }
    }

    public int getTotalCredits() {
        return totalCredits;
    }

    public void setTotalCredits(int totalCredits) {
        this.totalCredits = totalCredits;
    }

    public void addCart(Cart cart, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if(session!=null){
            session.setAttribute("cart", cart);
//            session.setMaxInactiveInterval(3600);
            session.setMaxInactiveInterval(-1);
        }

//
//        String sessionId = request.getSession().getId();
//        Cookie cookie = new Cookie("sessionID", sessionId);
//        response.addCookie(cookie);
    }
}

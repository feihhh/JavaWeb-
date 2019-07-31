package com.fei.domain;

/**
 * 购物车里面的条目
 */
public class CartItems {
    // 商品信息
    private Product product ;
    // 商品数量
    private Integer count ;
    // 商品小计
    private double subtotal;

    public CartItems() {
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }
}

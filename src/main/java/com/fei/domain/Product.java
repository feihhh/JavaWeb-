package com.fei.domain;

import java.io.Serializable;
import java.util.Date;

public class Product implements Serializable {

    // 商品id
    private String pid ;
    // 商品名
    private String pname ;
    // 商场价
    private double market_price ;
    // 打折之后的价
    private double shop_price;
    // 商品图片
    private String pimage ;
    // 生产日期
    private Date pdate ;
    // 是不是热门商品 1表示是热门商品 0表示不是
    private int is_hot ;
    // 商品描述
    private String pdexc ;
    // 是不是最新商品 0表示是，1表示不是
    private int pflag ;
    // 对应数据库字段cid  是一个外键
    private Category category ;

    public Product() {
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public double getMarket_price() {
        return market_price;
    }

    public void setMarket_price(double market_price) {
        this.market_price = market_price;
    }

    public double getShop_price() {
        return shop_price;
    }

    public void setShop_price(double shop_price) {
        this.shop_price = shop_price;
    }

    public String getPimage() {
        return pimage;
    }

    public void setPimage(String pimage) {
        this.pimage = pimage;
    }

    public Date getPdate() {
        return pdate;
    }

    public void setPdate(Date pdate) {
        this.pdate = pdate;
    }

    public int getIs_hot() {
        return is_hot;
    }

    public void setIs_hot(int is_hot) {
        this.is_hot = is_hot;
    }

    public String getPdexc() {
        return pdexc;
    }

    public void setPdexc(String pdexc) {
        this.pdexc = pdexc;
    }

    public int getPflag() {
        return pflag;
    }

    public void setPflag(int pflag) {
        this.pflag = pflag;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Product{" +
                "pid='" + pid + '\'' +
                ", pname='" + pname + '\'' +
                ", market_price=" + market_price +
                ", shop_price=" + shop_price +
                ", pimage='" + pimage + '\'' +
                ", pdate=" + pdate +
                ", is_hot=" + is_hot +
                ", pdexc='" + pdexc + '\'' +
                ", pflag=" + pflag +
                ", category=" + category +
                '}';
    }
}

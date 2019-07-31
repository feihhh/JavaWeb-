package com.fei.domain;

/**
 * | orderitem | CREATE TABLE `orderitem` (
 *   `itemid` varchar(32) NOT NULL,
 *   `count` int(11) DEFAULT NULL,
 *   `subtotal` double DEFAULT NULL,
 *   `pid` varchar(32) DEFAULT NULL,
 *   `oid` varchar(32) DEFAULT NULL,
 *   PRIMARY KEY (`itemid`),
 *   KEY `fk_0001` (`pid`),
 *   KEY `fk_0002` (`oid`),
 *   CONSTRAINT `fk_0001` FOREIGN KEY (`pid`) REFERENCES `product` (`pid`),
 *   CONSTRAINT `fk_0002` FOREIGN KEY (`oid`) REFERENCES `orders` (`oid`)
 * ) ENGINE=InnoDB DEFAULT CHARSET=utf8 |
 */
public class OrderItems {

    private String itemid ;

    private Integer count ;

    private double subtotal ;

    private String pid ;

    private Product product;

    private Orders orders;

    public OrderItems() {
    }

    public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    @Override
    public String toString() {
        return "OrderItems{" +
                "itemid='" + itemid + '\'' +
                ", count=" + count +
                ", subtotal=" + subtotal +
                ", product=" + product +
                ", orders=" + orders +
                '}';
    }
}

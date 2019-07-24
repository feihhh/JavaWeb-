package com.fei.domain;

import java.util.List;

/**
 * 一个分页组件，用来分页展示商品信息
 * @param <T>
 */
public class PageBean<T> {

    private Integer totalNum ; // 条目总数

    private Integer curPageNum ; // 一个页面存放的条目数

    private Integer totalPage; // 总页数

    private Integer curPage ; // 当前是第几页

    private List<T> list ; // 一个页面中存放的数据

    public PageBean() {

    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public Integer getCurPageNum() {
        return curPageNum;
    }

    public void setCurPageNum(Integer curPageNum) {
        this.curPageNum = curPageNum;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getCurPage() {
        return curPage;
    }

    public void setCurPage(Integer curPage) {
        this.curPage = curPage;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

}

package com.fei.dao.impl;

import com.fei.dao.IProductDao;
import com.fei.domain.PageBean;
import com.fei.domain.Product;
import com.fei.utils.DaoUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.util.List;

public class ProductDaoImpl implements IProductDao {

    QueryRunner qr = new QueryRunner(DaoUtil.getDatasource());

    /**
     * 通过类型查询一个页面的商品信息
     * @param bean 页面信息
     * @param cid 类别id
     * @return 返回List<Product>
     * @throws Exception
     */
    @Override
    public List<Product> findByPageAndCid(PageBean<Product> bean, String cid) throws Exception{
        String sql = "select * from product where cid = ? order by 'pdata' limit ?, ?";

        return qr.query(sql, new BeanListHandler<Product>(Product.class),cid, (bean.getCurPage()
                -1)*bean.getCurPageNum(), bean.getCurPageNum());
    }

    /**
     * 计算一类商品的总个数
     * @param cid 类别id
     * @return 返回该类商品的个数
     * @throws Exception
     */
    @Override
    public Integer computerCountByCid(String cid) throws Exception{
        String sql = "select count(*) from product where cid = ?" ;
        return ((Long) qr.query(sql, new ScalarHandler<>(), cid)).intValue();
    }

    /**
     * 通过pid查询商品信息
     * @param pid 商品pid
     * @return 返回单个商品
     * @throws Exception
     */
    @Override
    public Product findProByPid(String pid) throws Exception {
        String sql = "select * from product where pid = ?" ;
        return qr.query(sql, new BeanHandler<Product>(Product.class), pid);
    }

    /**
     * 查询最热商品
     * @return
     * @throws Exception
     */
    @Override
    public List<Product> findHotPro() throws Exception {
        String sql = "select * from product where is_hot = 1 limit 9" ;
        return qr.query(sql, new BeanListHandler<Product>(Product.class));
    }

    /**
     * 查询最新商品
     * @return
     * @throws Exception
     */
    @Override
    public List<Product> findNewPro() throws Exception {
        String sql = "select * from product where pflag = 0 limit 9" ;
        return qr.query(sql, new BeanListHandler<Product>(Product.class));
    }

    @Override
    public List<Product> findProByName(PageBean bean, String proName) throws Exception {
        String sql = "select * from product where pname like ? order by is_hot desc limit ?, ?";
        return qr.query(sql, new BeanListHandler<Product>(Product.class), "%"+proName+"%" , (bean
                .getCurPage()-1)*bean.getCurPageNum(), bean.getCurPageNum());
    }

    @Override
    public int proCountByName(String name) throws Exception {
        String sql = "select count(*) from product where pname like ?" ;
        return  ((Long)qr.query(sql, new ScalarHandler<>(), "%"+name+"%")).intValue();
    }
}


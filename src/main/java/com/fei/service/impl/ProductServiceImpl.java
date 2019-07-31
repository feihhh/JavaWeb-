package com.fei.service.impl;

import com.fei.dao.ICategoryDao;
import com.fei.dao.IProductDao;
import com.fei.dao.impl.CategoryDaoImpl;
import com.fei.dao.impl.ProductDaoImpl;
import com.fei.domain.Category;
import com.fei.domain.PageBean;
import com.fei.domain.Product;
import com.fei.service.IProductService;

import java.util.List;

public class ProductServiceImpl implements IProductService {

    private IProductDao pDao = new ProductDaoImpl();

    /**
     * 通过页数查询商品
     * @throws Exception
     */
    @Override
    public PageBean<Product> findByPageAndCid(String cid, int pageNum) throws Exception {
        PageBean<Product> bean = new PageBean<>();
        bean.setCurPage(pageNum);
        bean.setCurPageNum(12);
        bean.setTotalNum(pDao.computerCountByCid(cid));
        bean.setTotalPage((int)Math.ceil(bean.getTotalNum()*1.0 / bean.getCurPageNum()));
        List<Product> pList = pDao.findByPageAndCid(bean, cid);
        bean.setList(pList);
        return bean;
    }

    /**
     * 通过pid查询商品信息
     * @param pid
     * @return
     * @throws Exception
     */
    @Override
    public Product findProByPid(String pid) throws Exception {
        Product product = pDao.findProByPid(pid);
        return product;
    }

    /**
     * 查询热门商品
     * 限制数量最多为9个
     * @return
     */
    @Override
    public List<Product> findHotPro() throws Exception{
        return pDao.findHotPro();
    }

    /**
     * 查询最新商品
     * 限制数量最多为9个
     * @return
     */
    @Override
    public List<Product> findNewPro() throws Exception{
        return pDao.findNewPro();
    }

    @Override
    public PageBean<Product> findProByName(String proName, int pageNum) throws Exception {
        PageBean<Product> bean = new PageBean<>();
        bean.setCurPage(pageNum);
        bean.setCurPageNum(12);
        bean.setTotalNum(pDao.proCountByName(proName));
        bean.setTotalPage((int)Math.ceil(bean.getTotalNum()*1.0 / bean.getCurPageNum()));
        List<Product> pList = pDao.findProByName(bean, proName);
        bean.setList(pList);
        return bean;


//        return pDao.findProByName(proName);
    }
}

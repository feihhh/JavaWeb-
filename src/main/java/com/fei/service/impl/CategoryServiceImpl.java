package com.fei.service.impl;

import com.fei.dao.ICategoryDao;
import com.fei.dao.impl.CategoryDaoImpl;
import com.fei.domain.Category;
import com.fei.service.ICategoryService;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import java.util.List;

public class CategoryServiceImpl implements ICategoryService {

    private ICategoryDao categoryDao = new CategoryDaoImpl();

    @Override
    public List<Category> findAll() throws Exception{
//        直接在数据库中查
//        return categoryDao.findAll();
        // 这里使用缓存技术，将数据库中的信息存放再缓存中
        /**
         * 直接从数据库中查找的缺点：
         *  1、首先，我们将页面信息中的菜单栏和导航栏中的信息提取出来，然后使用静态导入的方式
         *  将提取出来的部分再导入到对应页面的对应位置，但是数据库中查询数据只是在加载首页
         *  的时候调用了数据库，加载其他页面的时候并没有进行在数据库中查询的操作，导致在其
         *  他页面，分类信息展示不出来 --- 解决方法：把它放一个单独的servlet中，页面加载的时候发送一个Ajax的异步请求
         *  2、目前这个项目只是在本地；加入有多用户访问首页的时候，如果每个用户都要在数据库中
         *  查询，会导致服务器压力过大，因此需要使用缓存
         */

        /**
         * 缓存的使用场景：不经常改变的数据
         *  导航条的加载（从数据库中第一次读取之后，放入到缓存）
         *  门户网站中的轮播广告（因为不能保证轮播广告一直不变，所以需要用到缓存同步）
         *  TOP排行榜
         */

        /**
         * 使用缓存的逻辑(步骤)：
         *  1）从缓存中获取数据（集合）
         *  2）判断集合中是否存在数据
         *      a.如果不存在：从数据库中读取数据，将数据写入缓存中
         *      b.如果存在：直接获取缓存中的数据
         */

        /**
         * 缓存同步：前台展示的是以前的数据，后台中的数据已经更新了
         *  将缓存中存储的表示名称从内存中删除，下次要从缓存中读取数据的时候发现是空的，就直接再从数据库中查新的数据
         */
        // CacheManager : 缓存处理器，需要通过缓存文件来创建对象
        CacheManager manager = CacheManager.create(CategoryServiceImpl.class
                .getClassLoader().getResourceAsStream("ehcache.xml"));
        // 通过缓存文件中的标识名称获取缓存对象
        Cache cache = manager.getCache("categoryCache");
        //
        Element element = cache.get("clist");
        // 声明list集合
        List<Category> list = null;
        // 判断缓存集合中是否为空
        if(element == null)
        {
           // 如果为空
            //从数据库中查
            list = categoryDao.findAll();
//            System.out.println("从数据库中获取值...");
            //将数据存放到缓存中
            cache.put(new Element("clist", list));
        }else {
            // 如果不为空 直接从缓存中获取
            list = (List<Category>) element.getObjectValue();
//            System.out.println("从缓存中获取值...");
        }
        return list;
    }

    @Override
    public Category findCategoryByCid(String cid) throws Exception {
        return categoryDao.findCateByCid(cid);
    }
}

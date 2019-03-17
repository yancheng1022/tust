package com.tust.sellergoods.service;

import com.github.pagehelper.PageInfo;
import com.tust.VO.GoodsVO;
import com.tust.pojo.Goods;
import com.tust.pojo.Item;

import java.util.List;

public interface GoodsService {

    /**
     * @Author: Yancheng Guo
     * @Date: 2019/1/15 8:44
     * @Description: 查询所有
    */
    public List<Goods> findAll();


    /**
     * @Author: Yancheng Guo
     * @Date: 2019/1/15 8:44
     * @Description: 返回分页列表
    */
    public PageInfo findPage(int pageNum, int pageSize);


    /**
     * @Author: Yancheng Guo
     * @Date: 2019/1/15 8:45
     * @Description: 
    */
    public void add(GoodsVO goodsVO);

    /**
     * @Author: Yancheng Guo
     * @Date: 2019/1/15 8:54
     * @Description: 商品更新
    */
    public void update(GoodsVO goodsVO);
    /**
     * @Author: Yancheng Guo
     * @Date: 2019/1/15 8:55
     * @Description: 获取实体
    */
    public GoodsVO findOne(Long id);

    /**
     * @Author: Yancheng Guo
     * @Date: 2019/1/15 8:55
     * @Description: 批量删除
    */
    public void delete(Long [] ids);

    /**
     * @Author: Yancheng Guo
     * @Date: 2019/1/15 8:56
     * @Description: 分页查询
    */
    public PageInfo findPage(Goods goods, int pageNum,int pageSize);
    /**
     * @Author: Yancheng Guo
     * @Date: 2019/1/18 19:27
     * @Description: 更改商品状态
    */
    public void updateStatus(Long[] ids,String status);
    public List<Item>	findItemListByGoodsIdListAndStatus(Long []goodsIds, String status);

    /**
     * @Author: Yancheng Guo
     * @Date: 2019/3/9 8:49
     * @Description: 查询商城直销商品
    */
    List<GoodsVO> findMallGoods();
}

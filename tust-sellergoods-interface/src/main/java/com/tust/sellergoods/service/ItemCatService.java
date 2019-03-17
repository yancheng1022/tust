package com.tust.sellergoods.service;

import com.github.pagehelper.PageInfo;
import com.tust.pojo.ItemCat;

import java.util.List;

/**
 * @Author: Yancheng Guo
 * @Date: 2019/1/16 8:35
 * @Description: 商品类目接口
*/
public interface ItemCatService {
    /**
     * @Author: Yancheng Guo
     * @Date: 2019/1/16 8:43
     * @Description: 查询所有类目
    */
    public List<ItemCat> findAll();
    /**
     * @Author: Yancheng Guo
     * @Date: 2019/1/16 8:44
     * @Description: 按分页查询
    */
    public PageInfo findPage(int pageNum, int pageSize);
    /**
     * @Author: Yancheng Guo
     * @Date: 2019/1/16 8:44
     * @Description: 增加
    */
    /**
     * @Author: Yancheng Guo
     * @Date: 2019/1/16 9:41
     * @Description: 增加
    */
    public void add(ItemCat itemCat);

    public void update(ItemCat itemCat);
    /**
     * @Author: Yancheng Guo
     * @Date: 2019/1/16 9:16
     * @Description: 根据id获取实体
    */
    public ItemCat findOne(Long id);
    /**
     * @Author: Yancheng Guo
     * @Date: 2019/1/16 9:27
     * @Description: 批量删除
    */
    public void delete(Long [] ids);
    /**
     * @Author: Yancheng Guo
     * @Date: 2019/1/16 9:27
     * @Description: 分页
    */
    public PageInfo findPage(ItemCat itemCat, int pageNum,int pageSize);
    /**
     * @Author: Yancheng Guo
     * @Date: 2019/1/16 9:28
     * @Description: 根据parentId查询商品分类列表
    */
    public List<ItemCat> findByParentId(Long parentId);
    /**
     * @Author: Yancheng Guo
     * @Date: 2019/3/7 15:27
     * @Description: 前端系统展示分类列表
    */
//    public CatResult getItemCatList();
}

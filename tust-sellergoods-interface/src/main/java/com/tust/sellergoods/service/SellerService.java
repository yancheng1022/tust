package com.tust.sellergoods.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.tust.pojo.Seller;

import java.util.List;

public interface SellerService {
    /**
     * @Author: Yancheng Guo
     * @Date: 2019/1/15 9:33
     * @Description: 查找所有
    */
    public List<Seller> findAll();


    /**
     * @Author: Yancheng Guo
     * @Date: 2019/1/15 9:34
     * @Description: 按分页查找
    */
    public PageInfo findPage(int pageNum, int pageSize);


    /**
     * @Author: Yancheng Guo
     * @Date: 2019/1/15 9:34
     * @Description: 增加
    */
    public void add(Seller seller);


    /**
     * @Author: Yancheng Guo
     * @Date: 2019/1/15 9:35
     * @Description: 更新
    */
    public void update(Seller seller);


    /**
     * @Author: Yancheng Guo
     * @Date: 2019/1/15 9:35
     * @Description: 查找某个
    */
    public Seller findOne(String id);

    /**
     * @Author: Yancheng Guo
     * @Date: 2019/1/15 9:35
     * @Description: 批量删除
    */
    public void delete(String [] ids);

    /**
     * @Author: Yancheng Guo
     * @Date: 2019/1/15 9:36
     * @Description: 查找
    */
    public PageInfo search(Seller seller, int pageNum, int pageSize);

    /**
     * @Author: Yancheng Guo
     * @Date: 2019/1/15 9:36
     * @Description: 更新状态
    */
    public void updateStatus(String sellerId,String status);
}

package com.tust.sellergoods.service;

import com.tust.pojo.Item;

import java.util.List;

public interface ItemService {

    /**
     * @Author: Yancheng Guo
     * @Date: 2019/3/20 14:32
     * @Description: 查询商品排行榜列表
    */
    public List<Item> findItemSorted();
}

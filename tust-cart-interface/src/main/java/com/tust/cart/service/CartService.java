package com.tust.cart.service;


import com.tust.VO.CartVo;

import java.util.List;

/**
 * @Author: Yancheng Guo
 * @Date: 2019/1/28 11:58
 * @Description: 购物车服务接口
*/
public interface CartService {
    /**
     * @Author: Yancheng Guo
     * @Date: 2019/1/28 12:12
     * @Description: 添加商品到购物车
    */
    public List<CartVo> addGoodsToCartList(List<CartVo>list,Long itemId,Integer num);

    /**
     * @Author: Yancheng Guo
     * @Date: 2019/1/28 17:36
     * @Description: 从redis中提取购物车
    */
    public List<CartVo> findCartListFromRedis(String userName);

    /**
     * @Author: Yancheng Guo
     * @Date: 2019/1/28 17:37
     * @Description: 将购物车列表存入redis
    */
    public void saveCartListToRedis(String userName,List<CartVo>cartVoList);

    /**
     * @Author: Yancheng Guo
     * @Date: 2019/1/28 18:35
     * @Description: 合并购物车
    */
    public List<CartVo> mergeCartList(List<CartVo>cartVoList1,List<CartVo>cartVoList2);
}

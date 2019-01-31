package com.tust.VO;

import com.tust.pojo.OrderItem;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: Yancheng Guo
 * @Date: 2019/1/28 11:50
 * @Description: 购物车对象
*/
@Data
public class CartVo implements Serializable {
    private String sellerId; //商家id
    private String sellerName;//商家名称
    private List<OrderItem> orderItemList;//购物车明细列表

}

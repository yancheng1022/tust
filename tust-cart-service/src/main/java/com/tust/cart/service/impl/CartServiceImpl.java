package com.tust.cart.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.tust.VO.CartVo;
import com.tust.cart.service.CartService;
import com.tust.mapper.ItemMapper;
import com.tust.pojo.Item;
import com.tust.pojo.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public List<CartVo> addGoodsToCartList(List<CartVo> cartList, Long itemId, Integer num) {

        //1.根据skuID查询商品明细SKU的对象
        Item item = itemMapper.selectByPrimaryKey(itemId);
        if(item==null){
            throw new RuntimeException("商品不存在");
        }
        if(!item.getStatus().equals("1")){
            throw new RuntimeException("商品状态不合法");
        }
        //2.根据SKU对象得到商家ID
        String sellerId = item.getSellerId();//商家ID

        //3.根据商家ID在购物车列表中查询购物车对象
        CartVo cartVo = searchCartBySellerId(cartList,sellerId);

        if(cartVo==null){//4.如果购物车列表中不存在该商家的购物车

            //4.1 创建一个新的购物车对象
            cartVo=new CartVo();
            cartVo.setSellerId(sellerId);//商家ID
            cartVo.setSellerName(item.getSeller());//商家名称
            List<OrderItem> orderItemList=new ArrayList();//创建购物车明细列表
            OrderItem orderItem = createOrderItem(item,num);
            orderItemList.add(orderItem);
            cartVo.setOrderItemList(orderItemList);

            //4.2将新的购物车对象添加到购物车列表中
            cartList.add(cartVo);

        }else{//5.如果购物车列表中存在该商家的购物车
            // 判断该商品是否在该购物车的明细列表中存在
            OrderItem orderItem = searchOrderItemByItemId(cartVo.getOrderItemList(),itemId);
            if(orderItem==null){
                //5.1  如果不存在  ，创建新的购物车明细对象，并添加到该购物车的明细列表中
                orderItem=createOrderItem(item,num);
                cartVo.getOrderItemList().add(orderItem);

            }else{
                //5.2 如果存在，在原有的数量上添加数量 ,并且更新金额
                orderItem.setNum(orderItem.getNum()+num);//更改数量
                //金额
                orderItem.setTotalFee( new BigDecimal(orderItem.getPrice().doubleValue()*orderItem.getNum() ) );
                //当明细的数量小于等于0，移除此明细
                if(orderItem.getNum()<=0){
                    cartVo.getOrderItemList().remove(orderItem);
                }
                //当购物车的明细数量为0，在购物车列表中移除此购物车
                if(cartVo.getOrderItemList().size()==0){
                    cartList.remove(cartVo);
                }
            }

        }

        return cartList;
    }

    @Override
    public List<CartVo> findCartListFromRedis(String userName) {
        System.out.println("从redis中提取购物车"+userName);
        List<CartVo> cartList = (List<CartVo>) redisTemplate.boundHashOps("cartList").get(userName);
        if(cartList==null){
            cartList=new ArrayList();
        }
        return cartList;
    }

    @Override
    public void saveCartListToRedis(String userName, List<CartVo> cartVoList) {
        System.out.println("向redis中存入购物车"+userName);
        redisTemplate.boundHashOps("cartList").put(userName, cartVoList);
    }

    @Override
    public List<CartVo> mergeCartList(List<CartVo> cartVoList1, List<CartVo> cartVoList2) {
        for(CartVo cart:cartVoList2){
            for( OrderItem orderItem :cart.getOrderItemList() ){
                cartVoList1=addGoodsToCartList(cartVoList1,orderItem.getItemId(),orderItem.getNum());
            }
        }
        return cartVoList1;
    }

    private CartVo searchCartBySellerId(List<CartVo> cartList,String sellerId){
        for(CartVo cart:cartList){
            if(cart.getSellerId().equals(sellerId)){
                return cart;
            }
        }
        return null;
    }

    private OrderItem createOrderItem(Item item,Integer num){
        //创建新的购物车明细对象
        OrderItem orderItem=new OrderItem();
        orderItem.setGoodsId(item.getGoodsId());
        orderItem.setItemId(item.getId());
        orderItem.setNum(num);
        orderItem.setPicPath(item.getImage());
        orderItem.setPrice(item.getPrice());
        orderItem.setSellerId(item.getSellerId());
        orderItem.setTitle(item.getTitle());
        orderItem.setTotalFee(  new BigDecimal(item.getPrice().doubleValue()*num) );
        return orderItem;
    }

    public OrderItem searchOrderItemByItemId(List<OrderItem> orderItemList,Long itemId){
        for(OrderItem orderItem:orderItemList){
            if(orderItem.getItemId().longValue()==itemId.longValue()){
                return orderItem;
            }
        }
        return null;
    }
}

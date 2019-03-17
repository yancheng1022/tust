package com.tust.portal.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.tust.VO.GoodsVO;
import com.tust.pojo.Goods;
import com.tust.sellergoods.service.GoodsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/goods")
public class GoodsController {
    @Reference
    private GoodsService goodsService;

    @RequestMapping("/findMallGoods")
    public List<GoodsVO> findMallGoods(){
        List<GoodsVO> mallGoods = goodsService.findMallGoods();
        return mallGoods;
    }
}

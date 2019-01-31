package com.tust.shop.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.tust.VO.GoodsVO;
import com.tust.pojo.Goods;
import com.tust.pojo.Result;
import com.tust.sellergoods.service.GoodsService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/goods")
public class GoodsController {
    @Reference
    private GoodsService goodsService;

    @RequestMapping("/findAll")
    public List<Goods> findAll(){
        return goodsService.findAll();
    }

    @RequestMapping("/findPage")
    public PageInfo findPage(int page,int rows){
        return goodsService.findPage(page,rows);
    }

    @RequestMapping("/add")
    public Result add(@RequestBody GoodsVO goodsVO){
        String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
        goodsVO.getGoods().setSellerId(sellerId);
        try {
            goodsService.add(goodsVO);
            return new Result(true, "增加成功");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, "增加失败");
        }
    }

    @RequestMapping("/update")
    public Result update(@RequestBody GoodsVO goodsVO){
        String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
        GoodsVO one = goodsService.findOne(goodsVO.getGoods().getId());
        if (!one.getGoods().getSellerId().equals(sellerId)){
            return new Result(false, "非法操作");
        }
        try {
            goodsService.update(goodsVO);
            return new Result(true, "修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "修改失败");
        }
    }

    @RequestMapping("/findOne")
    public GoodsVO findOne(Long id){
        GoodsVO one = goodsService.findOne(id);
        return one;
    }

    @RequestMapping("/delete")
    public Result delete(Long [] ids){
        try {
            goodsService.delete(ids);
            return new Result(true, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除失败");
        }
    }

    @RequestMapping("/search")
    public PageInfo search(@RequestBody Goods goods, int page, int rows  ){
        String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
        goods.setSellerId(sellerId);
        PageInfo page1 = goodsService.findPage(goods, page, rows);
        return page1;
    }
}

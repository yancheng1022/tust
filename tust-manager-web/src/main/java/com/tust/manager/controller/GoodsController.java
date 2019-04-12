package com.tust.manager.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.tust.VO.GoodsVO;
import com.tust.page.service.ItemPageService;
import com.tust.pojo.Goods;
import com.tust.pojo.Item;
import com.tust.pojo.Result;
import com.tust.search.service.ItemSearchService;
import com.tust.sellergoods.service.GoodsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/goods")
public class GoodsController {
    @Reference
    private GoodsService goodsService;
    @Reference
    private ItemSearchService itemSearchService;


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
            //从索引库中删除
            itemSearchService.deleteByGoodsIds(Arrays.asList(ids));
            return new Result(true, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除失败");
        }
    }

    @RequestMapping("/search")
    public PageInfo search(@RequestBody Goods goods, int page, int rows  ){
        return goodsService.findPage(goods, page, rows);
    }

    @RequestMapping("/updateStatus")
    public Result updateStatus(Long []ids,String status){
        try {
            goodsService.updateStatus(ids, status);
            if ("1".equals(status)){  //如果是1 审核通过
                //SKU列表
                List<Item> itemList = goodsService.findItemListByGoodsIdListAndStatus(ids, status);

                itemSearchService.importList(itemList);

                //生成商品详情页
                for (Long id:ids){
                    itemPageService.genItemHtml(id);
                }

            }
            return new Result(true, "成功");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, "失败");
        }

    }

    @Reference(timeout=40000)
    private ItemPageService itemPageService;

    @RequestMapping("/genHtml")
    public void genHtml(Long goodsId){

        itemPageService.genItemHtml(goodsId);

    }
}

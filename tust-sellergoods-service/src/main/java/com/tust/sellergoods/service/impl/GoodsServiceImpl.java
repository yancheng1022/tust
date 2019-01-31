package com.tust.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tust.VO.GoodsVO;
import com.tust.mapper.*;
import com.tust.pojo.*;
import com.tust.sellergoods.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.*;
import java.awt.*;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
//@Transactional
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private GoodsDescMapper goodsDescMapper;
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private ItemCatMapper itemCatMapper;
    @Autowired
    private BrandMapper brandMapper;
    @Autowired
    private SellerMapper sellerMapper;

    @Override
    public List<Goods> findAll() {
        return goodsMapper.selectByExample(null);
    }

    @Override
    public PageInfo findPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Goods> goods = goodsMapper.selectByExample(null);
        return new PageInfo(goods);
    }

    @Override
    public void add(GoodsVO goodsVO) {
        goodsVO.getGoods().setAuditStatus("0"); //状态：未审核
        goodsMapper.insert(goodsVO.getGoods());

        goodsVO.getGoodsDesc().setGoodsId(goodsVO.getGoods().getId()); //商品表id传给扩展表
        goodsDescMapper.insert(goodsVO.getGoodsDesc());  //插入商品扩展表数据

        saveItemList(goodsVO);

    }

    private void saveItemList(GoodsVO goodsVO){
        if ("1".equals(goodsVO.getGoods().getIsEnableSpec())){
            List<Item> itemList = goodsVO.getItemList();
            for (Item item:itemList) {
                //构建标题 SPU+规格选项名称
                String title = goodsVO.getGoods().getGoodsName();
                Map<String, Object> map = JSON.parseObject(item.getSpec());
                for (String key : map.keySet()) {
                    title += " " + map.get(key);
                }
                item.setTitle(title);
                setItemValues(item,goodsVO);
                itemMapper.insert(item);
            }
        }else {
            Item item=new Item();
            item.setTitle(goodsVO.getGoods().getGoodsName());//标题
            item.setPrice(goodsVO.getGoods().getPrice());//价格
            item.setNum(99999);//库存数量
            item.setStatus("1");//状态
            item.setIsDefault("1");//默认
            item.setSpec("{}");//规格
            setItemValues(item,goodsVO);
            itemMapper.insert(item);
        }
    }

    private void setItemValues(Item item,GoodsVO goodsVO){
        //商品分类
        item.setCategoryid(goodsVO.getGoods().getCategory3Id());
        item.setCreateTime(new Date());
        item.setUpdateTime(new Date());
        //商品id
        item.setGoodsId(goodsVO.getGoods().getId());
        //商家id
        item.setSellerId(goodsVO.getGoods().getSellerId());
        //商品分类
        ItemCat itemCat = itemCatMapper.selectByPrimaryKey(goodsVO.getGoods().getCategory3Id());
        item.setCategory(itemCat.getName());
        //品牌
        Brand brand = brandMapper.selectByPrimaryKey(goodsVO.getGoods().getBrandId());
        item.setBrand(brand.getName());
        //商家店铺名
        Seller seller = sellerMapper.selectByPrimaryKey(goodsVO.getGoods().getSellerId());
        item.setSeller(seller.getNickName());
        //图片
        List<Map> list = JSON.parseArray(goodsVO.getGoodsDesc().getItemImages(), Map.class);
        if (list.size()>0){
            item.setImage((String) list.get(0).get("url"));
        }
    }

    @Override
    public void update(GoodsVO goodsVO) {
        goodsMapper.updateByPrimaryKey(goodsVO.getGoods());
        goodsDescMapper.updateByPrimaryKey(goodsVO.getGoodsDesc());
        //删除原来的SKU列表
        ItemExample itemExample = new ItemExample();
        ItemExample.Criteria criteria = itemExample.createCriteria();
        criteria.andGoodsIdEqualTo(goodsVO.getGoods().getId());
        itemMapper.deleteByExample(itemExample);
        //插入新的SKU
        saveItemList(goodsVO);
    }

    @Override
    public GoodsVO findOne(Long id) {
        //基本表
        GoodsVO goodsVO = new GoodsVO();
        Goods goods = goodsMapper.selectByPrimaryKey(id);
        goodsVO.setGoods(goods);
        //扩展表
        GoodsDesc goodsDesc = goodsDescMapper.selectByPrimaryKey(id);
        goodsVO.setGoodsDesc(goodsDesc);
        //SKU列表
        ItemExample itemExample = new ItemExample();
        ItemExample.Criteria criteria = itemExample.createCriteria();
        criteria.andGoodsIdEqualTo(id);
        List<Item> items = itemMapper.selectByExample(itemExample);
        goodsVO.setItemList(items);
        return goodsVO;
    }

    @Override
    public void delete(Long[] ids) {
        for (Long id:ids){
            Goods goods = goodsMapper.selectByPrimaryKey(id);
            goods.setIsDelete("1");
            goodsMapper.updateByPrimaryKey(goods);
        }
    }

    @Override
    public PageInfo findPage(Goods goods, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        GoodsExample example=new GoodsExample();
        GoodsExample.Criteria criteria = example.createCriteria();
        criteria.andIsDeleteIsNull();//指定条件为未逻辑删除记录
        if(goods!=null){
            if(goods.getSellerId()!=null && goods.getSellerId().length()>0){
                criteria.andSellerIdEqualTo(goods.getSellerId());
            }
            if(goods.getGoodsName()!=null && goods.getGoodsName().length()>0){
                criteria.andGoodsNameLike("%"+goods.getGoodsName()+"%");
            }
            if(goods.getAuditStatus()!=null && goods.getAuditStatus().length()>0){
                criteria.andAuditStatusLike("%"+goods.getAuditStatus()+"%");
            }
            if(goods.getIsMarketable()!=null && goods.getIsMarketable().length()>0){
                criteria.andIsMarketableLike("%"+goods.getIsMarketable()+"%");
            }
            if(goods.getCaption()!=null && goods.getCaption().length()>0){
                criteria.andCaptionLike("%"+goods.getCaption()+"%");
            }
            if(goods.getSmallPic()!=null && goods.getSmallPic().length()>0){
                criteria.andSmallPicLike("%"+goods.getSmallPic()+"%");
            }
            if(goods.getIsEnableSpec()!=null && goods.getIsEnableSpec().length()>0){
                criteria.andIsEnableSpecLike("%"+goods.getIsEnableSpec()+"%");
            }
            if(goods.getIsDelete()!=null && goods.getIsDelete().length()>0){
                criteria.andIsDeleteLike("%"+goods.getIsDelete()+"%");
            }

        }
        List<Goods> page = goodsMapper.selectByExample(example);
        return new PageInfo(page);
    }

    @Override
    public void updateStatus(Long[] ids,String status) {
        for (Long id:ids){
            Goods goods = goodsMapper.selectByPrimaryKey(id);
            goods.setAuditStatus(status);
            goodsMapper.updateByPrimaryKey(goods);
        }
    }

    /**
     * 根据SPU的ID集合查询SKU列表
     * @param goodsIds
     * @param status
     * @return
     */
    @Override
    public List<Item>	findItemListByGoodsIdListAndStatus(Long []goodsIds,String status){

        ItemExample example=new ItemExample();
        com.tust.pojo.ItemExample.Criteria criteria = example.createCriteria();
        criteria.andStatusEqualTo(status);//状态
        criteria.andGoodsIdIn( Arrays.asList(goodsIds));//指定条件：SPUID集合

        return itemMapper.selectByExample(example);
    }
}

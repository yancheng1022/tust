package com.tust.page.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.tust.mapper.GoodsDescMapper;
import com.tust.mapper.GoodsMapper;
import com.tust.mapper.ItemCatMapper;
import com.tust.mapper.ItemMapper;
import com.tust.page.service.ItemPageService;
import com.tust.pojo.Goods;
import com.tust.pojo.GoodsDesc;
import com.tust.pojo.Item;
import com.tust.pojo.ItemExample;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ItemPageServiceImpl implements ItemPageService {

    @Autowired
    private FreeMarkerConfig freeMarkerConfig;
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private GoodsDescMapper goodsDescMapper;
    @Autowired
    private ItemCatMapper itemCatMapper;
    @Autowired
    private ItemMapper itemMapper;

    @Value("${pagedir}")
    private String pagedir;
    @Override
    public boolean genItemHtml(Long goodsId) {
        Configuration configuration = freeMarkerConfig.getConfiguration();
        try {
            Template template = configuration.getTemplate("item.ftl");
            //创建数据模型
            Map map = new HashMap<>();
            Goods goods = goodsMapper.selectByPrimaryKey(goodsId);

            GoodsDesc goodsDesc = goodsDescMapper.selectByPrimaryKey(goodsId);

            String itemCat1 = itemCatMapper.selectByPrimaryKey(goods.getCategory1Id()).getName();
            String itemCat2 = itemCatMapper.selectByPrimaryKey(goods.getCategory2Id()).getName();
            String itemCat3 = itemCatMapper.selectByPrimaryKey(goods.getCategory3Id()).getName();

            ItemExample itemExample = new ItemExample();
            ItemExample.Criteria criteria = itemExample.createCriteria();
            criteria.andGoodsIdEqualTo(goodsId);
            criteria.andStatusEqualTo("1");
            itemExample.setOrderByClause("is_default desc");//降序排序
            List<Item> itemList = itemMapper.selectByExample(itemExample);
            //商品主表数据
            map.put("goods", goods);
            //商品详情表数据
            map.put("goodsDesc", goodsDesc);
            //商品分类
            map.put("itemCat1", itemCat1);
            map.put("itemCat2", itemCat2);
            map.put("itemCat3", itemCat3);
            //SKU列表
            map.put("itemList",itemList);
            Writer out = new FileWriter(pagedir+goodsId+".html");
            template.process(map, out);
            out.close();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

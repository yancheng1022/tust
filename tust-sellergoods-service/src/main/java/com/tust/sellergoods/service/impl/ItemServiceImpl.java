package com.tust.sellergoods.service.impl;

import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlRenameTableStatement;
import com.alibaba.dubbo.config.annotation.Service;
import com.tust.mapper.ItemMapper;
import com.tust.pojo.Item;
import com.tust.pojo.ItemExample;
import com.tust.sellergoods.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public List<Item> findItemSorted() {
        ArrayList<Item> itemList = new ArrayList<>();
        Set<Long> itemSort = redisTemplate.boundZSetOps("itemSort").rangeByScore(0, 4);
        for (long itemId:itemSort){
            Item item = itemMapper.selectByPrimaryKey(itemId);
            itemList.add(item);
        }
        return itemList;
    }

    @Override
    public void updateStatus(Long[] ids,String status) {
        for (Long id:ids){
            ItemExample itemExample = new ItemExample();
            ItemExample.Criteria criteria = itemExample.createCriteria();
            criteria.andGoodsIdEqualTo(id);
            List<Item> itemList = itemMapper.selectByExample(itemExample);
            for (Item item:itemList){
                item.setStatus(status);
                itemMapper.updateByPrimaryKey(item);
            }
        }
    }
}

package com.tust.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tust.mapper.ItemCatMapper;
import com.tust.pojo.ItemCat;
import com.tust.pojo.ItemCatExample;
import com.tust.pojo.PageResult;
import com.tust.sellergoods.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(timeout = 999999999)
//@Transactional
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired
    private ItemCatMapper itemCatMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<ItemCat> findAll() {
        return itemCatMapper.selectByExample(null);
    }


    @Override
    public PageInfo findPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ItemCat> itemCats = itemCatMapper.selectByExample(null);
        return new PageInfo(itemCats);
    }

    @Override
    public void add(ItemCat itemCat)
    {
        itemCatMapper.insert(itemCat);
    }


    @Override
    public void update(ItemCat itemCat){
        itemCatMapper.updateByPrimaryKey(itemCat);
    }


    @Override
    public ItemCat findOne(Long id){
        return itemCatMapper.selectByPrimaryKey(id);
    }


    @Override
    public void delete(Long[] ids) {
        for(Long id:ids){
            itemCatMapper.deleteByPrimaryKey(id);
        }
    }


    @Override
    public PageInfo findPage(ItemCat itemCat, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        ItemCatExample example=new ItemCatExample();
        ItemCatExample.Criteria criteria = example.createCriteria();

        if(itemCat!=null){
            if(itemCat.getName()!=null && itemCat.getName().length()>0){
                criteria.andNameLike("%"+itemCat.getName()+"%");
            }

        }

        List<ItemCat> itemCats = itemCatMapper.selectByExample(example);
        return new PageInfo(itemCats);
    }

    @Override
    public List<ItemCat> findByParentId(Long parentId) {
        ItemCatExample example=new ItemCatExample();
        ItemCatExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);

//        System.out.println("将模板ID放入缓存");
//
//        List<ItemCat> itemCatList = findAll();
//        for(ItemCat itemCat:itemCatList){
//            redisTemplate.boundHashOps("itemCat").put(itemCat.getName(), itemCat.getTypeId());
//        }
//        System.out.println("将模板ID放入缓存");
        return itemCatMapper.selectByExample(example);
    }

}

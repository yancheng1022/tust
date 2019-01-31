package com.tust.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tust.mapper.BrandMapper;
import com.tust.pojo.Brand;
import com.tust.pojo.BrandExample;
import com.tust.pojo.BrandExample.*;
import com.tust.pojo.Result;
import com.tust.sellergoods.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Map;

@Service
//@Transactional
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandMapper brandMapper;


    @Override
    public List<Brand> findAll() {
        List<Brand> brands = brandMapper.selectByExample(null);
        return brands;
    }



    public PageInfo<Brand> findPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Brand> brands = brandMapper.selectByExample(null);
        PageInfo<Brand> pageInfo = new PageInfo<>(brands);
        return pageInfo;
    }


    public void add(Brand brand) {
        brandMapper.insert(brand);

    }


    public Brand findOne(Long id){
        return brandMapper.selectByPrimaryKey(id);
    }


    public void update(Brand brand){
        brandMapper.updateByPrimaryKey(brand);
    }


    public void delete(Long[] ids) {
        for (Long id:ids){
            brandMapper.deleteByPrimaryKey(id);
        }
    }


    @Override
    public PageInfo search(Brand brand, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        BrandExample brandExample = new BrandExample();
        Criteria criteria = brandExample.createCriteria();
        if (brand != null){
            if (brand.getName()!=null&& brand.getName().length()>0){
                criteria.andNameLike("%"+brand.getName()+"%");
            }
            if (brand.getFirstChar()!=null&&brand.getFirstChar().length()>0){
                criteria.andFirstCharLike("%"+brand.getFirstChar()+"%");
            }
        }
        List<Brand> brands = brandMapper.selectByExample(brandExample);
        return new PageInfo(brands);
    }


    public List<Map> selectOptionList() {
        return brandMapper.selectOptionList();
    }
}

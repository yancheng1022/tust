package com.tust.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tust.mapper.SpecificationMapper;
import com.tust.mapper.TypeTemplateMapper;
import com.tust.mapper.specificationOptionMapper;
import com.tust.pojo.TypeTemplate;
import com.tust.pojo.TypeTemplateExample;
import com.tust.pojo.specificationOption;
import com.tust.pojo.specificationOptionExample;
import com.tust.sellergoods.service.TypeTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service(timeout = 999999999)
//@Transactional
public class TypeTemplateServiceImpl implements TypeTemplateService {

    @Autowired
    private TypeTemplateMapper typeTemplateMapper;

    @Autowired
    private specificationOptionMapper specificationoptionMapper;

    public List<TypeTemplate> findAll() {
        return typeTemplateMapper.selectByExample(null);
    }

    public PageInfo findPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<TypeTemplate> typeTemplates = typeTemplateMapper.selectByExample(null);
        return new PageInfo(typeTemplates);
    }

    public void add(TypeTemplate typeTemplate) {
        typeTemplateMapper.insert(typeTemplate);
    }

    public void update(TypeTemplate typeTemplate) {
        typeTemplateMapper.updateByPrimaryKey(typeTemplate);
    }


    public TypeTemplate findOne(Long id) {
        return typeTemplateMapper.selectByPrimaryKey(id);
    }

    public void delete(Long[] ids) {
        for (Long id:ids){
            typeTemplateMapper.deleteByPrimaryKey(id);
        }
    }


    public PageInfo findPage(TypeTemplate typeTemplate, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        TypeTemplateExample example=new TypeTemplateExample();
        TypeTemplateExample.Criteria criteria = example.createCriteria();

        if(typeTemplate!=null){
            if(typeTemplate.getName()!=null && typeTemplate.getName().length()>0){
                criteria.andNameLike("%"+typeTemplate.getName()+"%");
            }
            if(typeTemplate.getSpecIds()!=null && typeTemplate.getSpecIds().length()>0){
                criteria.andSpecIdsLike("%"+typeTemplate.getSpecIds()+"%");
            }
            if(typeTemplate.getBrandIds()!=null && typeTemplate.getBrandIds().length()>0){
                criteria.andBrandIdsLike("%"+typeTemplate.getBrandIds()+"%");
            }
            if(typeTemplate.getCustomAttributeItems()!=null && typeTemplate.getCustomAttributeItems().length()>0){
                criteria.andCustomAttributeItemsLike("%"+typeTemplate.getCustomAttributeItems()+"%");
            }

        }
        List<TypeTemplate> typeTemplates = typeTemplateMapper.selectByExample(example);
        //缓存数据初始化
        saveToRedis();
        System.out.println("缓存处理");
        return new PageInfo(typeTemplates);
    }
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 将品牌列表与规格列表放入缓存
     */
    private void saveToRedis(){
        List<TypeTemplate> templateList = findAll();
        for(TypeTemplate template:templateList){
            //得到品牌列表
            List brandList= JSON.parseArray(template.getBrandIds(), Map.class) ;
            redisTemplate.boundHashOps("brandList").put(template.getId(), brandList);

            //得到规格列表
            List<Map> specList = findSpecList(template.getId());
            redisTemplate.boundHashOps("specList").put(template.getId(), specList);

        }
        System.out.println("缓存品牌列表");

    }

    @Override
    public List<Map> findSpecList(Long id) {
        //根据ID查询到模板对象
        TypeTemplate typeTemplate = typeTemplateMapper.selectByPrimaryKey(id);
        // 获得规格的数据spec_ids
        String specIds = typeTemplate.getSpecIds();// [{"id":27,"text":"网络"},{"id":32,"text":"机身内存"}]
        // 将specIds的字符串转成JSON的List<Map>
        List<Map> list = JSON.parseArray(specIds, Map.class);
        // 获得每条记录:
        for (Map map : list) {
            // 根据规格的ID 查询规格选项的数据:
            // 设置查询条件:
            specificationOptionExample example = new specificationOptionExample();
            com.tust.pojo.specificationOptionExample.Criteria criteria = example.createCriteria();
            criteria.andSpecIdEqualTo(new Long((Integer)map.get("id")));

            List<specificationOption> specOptionList = specificationoptionMapper.selectByExample(example);

            map.put("options", specOptionList);//{"id":27,"text":"网络",options:[{id：xxx,optionName:移动2G}]}
        }
        return list;
    }


}

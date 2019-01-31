package com.tust.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tust.VO.SpecificationVO;
import com.tust.mapper.SpecificationMapper;
import com.tust.mapper.specificationOptionMapper;
import com.tust.pojo.Specification;
import com.tust.pojo.SpecificationExample;
import com.tust.pojo.specificationOption;
import com.tust.pojo.specificationOptionExample;
import com.tust.sellergoods.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
//@Transactional
public class SpecificationServiceImpl implements SpecificationService {

    @Autowired
    private SpecificationMapper specificationMapper;
    @Autowired
    private specificationOptionMapper specificationOptionMapper;

    public List<Specification> findAll() {
        return specificationMapper.selectByExample(null);
    }


    public PageInfo<Specification> findPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Specification> specifications = specificationMapper.selectByExample(null);
        return new PageInfo(specifications);
    }

    public void add(SpecificationVO specificationVO) {
        //获取规格实体
        Specification specification = specificationVO.getSpecification();
        specificationMapper.insert(specification);

        // 获取规格选项集合
        List<specificationOption> specificationOptionList = specificationVO.getSpecificationOptionList();
        for (specificationOption specificationOption:specificationOptionList){
            specificationOption.setSpecId(specification.getId());
            specificationOptionMapper.insert(specificationOption);
        }
    }

    public void update(SpecificationVO specificationVO) {
        //获取规格实体
        Specification specification = specificationVO.getSpecification();
        specificationMapper.updateByPrimaryKey(specification);

        //删除原来规格对应的规格选项
        specificationOptionExample specificationOptionExample = new specificationOptionExample();
        com.tust.pojo.specificationOptionExample.Criteria criteria = specificationOptionExample.createCriteria();
        criteria.andSpecIdEqualTo(specification.getId());
        specificationOptionMapper.deleteByExample(specificationOptionExample);

        //获取规格选项集合
        List<specificationOption> specificationOptionList = specificationVO.getSpecificationOptionList();
        for (specificationOption specificationOption:specificationOptionList){
            specificationOption.setSpecId(specification.getId());
            specificationOptionMapper.insert(specificationOption);
        }
    }

    public SpecificationVO findOne(Long id) {
        SpecificationVO specificationVO = new SpecificationVO();
        //获取规格实体
        Specification specification = specificationMapper.selectByPrimaryKey(id);
        specificationVO.setSpecification(specification);

        //获取规格选项列表
        specificationOptionExample specificationOptionExample = new specificationOptionExample();
        com.tust.pojo.specificationOptionExample.Criteria criteria = specificationOptionExample.createCriteria();
        criteria.andSpecIdEqualTo(id);
        List<specificationOption> specificationOptions = specificationOptionMapper.selectByExample(specificationOptionExample);
        specificationVO.setSpecificationOptionList(specificationOptions);
        return specificationVO;
    }


    public void delete(Long[] ids) {
        for (Long id:ids){
            //删除规格表数据
            specificationMapper.deleteByPrimaryKey(id);
            //删除规格选项表数据
            specificationOptionExample specificationOptionExample = new specificationOptionExample();
            com.tust.pojo.specificationOptionExample.Criteria criteria = specificationOptionExample.createCriteria();
            criteria.andSpecIdEqualTo(id);
            specificationOptionMapper.deleteByExample(specificationOptionExample);
        }
    }


    public PageInfo search(Specification specification, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        SpecificationExample specificationExample = new SpecificationExample();
        SpecificationExample.Criteria criteria = specificationExample.createCriteria();

        if (specification != null){
            if (specification.getSpecName()!=null && specification.getSpecName().length()>0){
                criteria.andSpecNameLike("%"+specification.getSpecName()+"%");
            }
        }
        List<Specification> specifications = specificationMapper.selectByExample(specificationExample);
        return new PageInfo(specifications);
    }


    public List<Map> selectOptionList() {
        return specificationMapper.selectOptionList();
    }


}

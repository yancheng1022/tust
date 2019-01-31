package com.tust.sellergoods.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tust.VO.SpecificationVO;
import com.tust.pojo.Specification;

import java.util.List;
import java.util.Map;

/**
 * @Author: Yancheng Guo
 * @Date: 2019/1/9 10:56
 * @Description: 规格服务层接口
 */
public interface SpecificationService {
    /**
     * @Author: Yancheng Guo
     * @Date: 2019/1/9 10:57
     * @Description: 返回全部列表
     */
    public List<Specification> findAll();

    /**
     * @Author: Yancheng Guo
     * @Date: 2019/1/9 11:03
     * @Description: 按分页查询
     */
    public PageInfo findPage(int pageNum,int pageSize);
    
    /**
     * @Author: Yancheng Guo
     * @Date: 2019/1/9 11:08
     * @Description: 增加规格
     */
    public void add(SpecificationVO specificationVO);

    /**
     * @Author: Yancheng Guo
     * @Date: 2019/1/9 11:15
     * @Description: 修改规格
     */
    public void update(SpecificationVO specificationVO);

    /**
     * @Author: Yancheng Guo
     * @Date: 2019/1/9 13:52
     * @Description: 根据ID获取实体
     */
    public SpecificationVO findOne(Long id);

    /**
     * @Author: Yancheng Guo
     * @Date: 2019/1/9 13:58
     * @Description: 批量删除
     */
    public void delete(Long[] ids);

    /**
     * @Author: Yancheng Guo
     * @Date: 2019/1/9 14:04
     * @Description: 查询
     */
    public PageInfo search(Specification specification,int pageNum,int pageSize);

    /**
     * @Author: Yancheng Guo
     * @Date: 2019/1/14 14:19
     * @Description: 查询规格选项列表(模板管理)
    */
    public List<Map> selectOptionList();

}

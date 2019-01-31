package com.tust.sellergoods.service;

import com.github.pagehelper.PageInfo;
import com.tust.pojo.TypeTemplate;

import java.util.List;
import java.util.Map;

public interface TypeTemplateService {
    
    /**
     * @Author: Yancheng Guo
     * @Date: 2019/1/11 8:21
     * @Description: 返回全部列表
     */
    public List<TypeTemplate> findAll();

    /**
     * @Author: Yancheng Guo
     * @Date: 2019/1/11 8:26
     * @Description: 按分页查询
     */
    public PageInfo findPage(int pageNum, int pageSize);

    /**
     * @Author: Yancheng Guo
     * @Date: 2019/1/11 8:28
     * @Description: 增加
     */
    public void add(TypeTemplate typeTemplate);
    /**
     * @Author: Yancheng Guo
     * @Date: 2019/1/11 8:30
     * @Description: 修改
     */
    public void update(TypeTemplate typeTemplate);
    /**
     * @Author: Yancheng Guo
     * @Date: 2019/1/11 9:15
     * @Description: 根据id获取实体
     */
    public TypeTemplate findOne(Long id);
    /**
     * @Author: Yancheng Guo
     * @Date: 2019/1/11 9:16
     * @Description: 批量删除
     */
    public void delete(Long[] ids);
    /**
     * @Author: Yancheng Guo
     * @Date: 2019/1/11 9:21
     * @Description: 查询
     */
    public PageInfo findPage(TypeTemplate typeTemplate, int pageNum, int pageSize);
    /**
     * @Author: Yancheng Guo
     * @Date: 2019/1/17 19:46
     * @Description: 规格列表
    */
    public List<Map> findSpecList(Long id);
}

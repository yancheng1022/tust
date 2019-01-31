package com.tust.sellergoods.service;



import com.github.pagehelper.PageInfo;
import com.tust.pojo.Brand;

import java.util.List;
import java.util.Map;

/**
 * @Author: Yancheng Guo
 * @Date: 2019/1/7 22:12
 * @Description: 品牌接口
 */
public interface BrandService {
     /**
      * @Author: Yancheng Guo
      * @Date: 2019/1/9 10:58
      * @Description: 返回全部品牌
      */
     public List<Brand> findAll();

     /**
      * @Author: Yancheng Guo
      * @Date: 2019/1/8 16:58
      * @Description: 品牌分页
      */
     public PageInfo<Brand> findPage(int pageNum, int pageSize);

     /**
      * @Author: Yancheng Guo
      * @Date: 2019/1/8 19:46
      * @Description: 增加品牌
      */
     public void add(Brand brand);

     /**
      * @Author: Yancheng Guo
      * @Date: 2019/1/8 20:32
      * @Description: 查询品牌
      */
     public Brand findOne(Long id);

     /**
      * @Author: Yancheng Guo
      * @Date: 2019/1/8 20:34
      * @Description: 修改品牌
      */
     public void update(Brand brand);

     /**
      * @Author: Yancheng Guo
      * @Date: 2019/1/8 20:47
      * @Description: 删除品牌
      */
     public void delete(Long[] ids);

     /**
      * @Author: Yancheng Guo
      * @Date: 2019/1/8 20:58
      * @Description: 根据条件查询品牌（品牌名+首字母）
      */
     public PageInfo search(Brand brand,int pageNum,int pageSize);

     /**
      * @Author: Yancheng Guo
      * @Date: 2019/1/8 21:01
      * @Description: 查询品牌列表（模板管理）
     */
     public List<Map> selectOptionList();

}

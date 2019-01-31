package com.tust.content.service.impl;
import java.util.List;

import com.github.pagehelper.PageInfo;
import com.tust.content.service.ContentCategoryService;
import com.tust.mapper.ContentCategoryMapper;
import com.tust.pojo.ContentCategory;
import com.tust.pojo.ContentCategoryExample;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;


/**
 * @Author: Yancheng Guo
 * @Date: 2019/1/19 21:38
 * @Description: 服务层实现
*/
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

	@Autowired
	private ContentCategoryMapper contentCategoryMapper;
	
	/**
	 * 查询全部
	 */
	@Override
	public List<ContentCategory> findAll() {
		return contentCategoryMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageInfo findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<ContentCategory> contentCategories = contentCategoryMapper.selectByExample(null);
		return new PageInfo(contentCategories);
	}

	/**
	 * 增加
	 */
	@Override
	public void add(ContentCategory contentCategory) {
		contentCategoryMapper.insert(contentCategory);		
	}

	
	/**
	 * 修改
	 */
	@Override
	public void update(ContentCategory contentCategory){
		contentCategoryMapper.updateByPrimaryKey(contentCategory);
	}	
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public ContentCategory findOne(Long id){
		return contentCategoryMapper.selectByPrimaryKey(id);
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(Long[] ids) {
		for(Long id:ids){
			contentCategoryMapper.deleteByPrimaryKey(id);
		}		
	}
	
	
		@Override
	public PageInfo findPage(ContentCategory contentCategory, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		ContentCategoryExample example=new ContentCategoryExample();
		ContentCategoryExample.Criteria criteria = example.createCriteria();
		
		if(contentCategory!=null){			
						if(contentCategory.getName()!=null && contentCategory.getName().length()>0){
				criteria.andNameLike("%"+contentCategory.getName()+"%");
			}
	
		}

			List<ContentCategory> contentCategories = contentCategoryMapper.selectByExample(example);
			return new PageInfo(contentCategories);
	}
	
}

package com.tust.content.service;
import com.github.pagehelper.PageInfo;
import com.tust.pojo.ContentCategory;

import java.util.List;

public interface ContentCategoryService {

	/**
	 * @Author: Yancheng Guo
	 * @Date: 2019/1/19 21:38
	 * @Description: 返回全部列表
	*/
	public List<ContentCategory> findAll();
	
	
	/**
	 * @Author: Yancheng Guo
	 * @Date: 2019/1/19 21:38
	 * @Description: 返回分页列表
	*/
	public PageInfo findPage(int pageNum, int pageSize);
	
	
	/**
	 * @Author: Yancheng Guo
	 * @Date: 2019/1/19 21:39
	 * @Description: 增加
	*/
	public void add(ContentCategory contentCategory);
	
	
	/**
	 * @Author: Yancheng Guo
	 * @Date: 2019/1/19 21:39
	 * @Description: 修改
	*/
	public void update(ContentCategory contentCategory);
	

	/**
	 * @Author: Yancheng Guo
	 * @Date: 2019/1/19 21:39
	 * @Description: 根据id获取实体
	*/
	public ContentCategory findOne(Long id);
	
	
	/**
	 * @Author: Yancheng Guo
	 * @Date: 2019/1/19 21:39
	 * @Description: 批量删除
	*/
	public void delete(Long[] ids);

	/**
	 * @Author: Yancheng Guo
	 * @Date: 2019/1/19 21:39
	 * @Description: 分页
	*/
	public PageInfo findPage(ContentCategory contentCategory, int pageNum, int pageSize);
	
}

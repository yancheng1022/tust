package com.tust.content.service;
import com.github.pagehelper.PageInfo;
import com.tust.pojo.Content;

import java.util.List;

/**
 * 服务层接口
 * @author Administrator
 *
 */
public interface ContentService {

	/**
	 * 返回全部列表
	 * @return
	 */
	public List<Content> findAll();
	
	
	/**
	 * 返回分页列表
	 * @return
	 */
	public PageInfo findPage(int pageNum, int pageSize);
	
	
	/**
	 * 增加
	*/
	public void add(Content content);
	
	
	/**
	 * 修改
	 */
	public void update(Content content);
	

	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	public Content findOne(Long id);
	
	
	/**
	 * 批量删除
	 * @param ids
	 */
	public void delete(Long[] ids);

	/**
	 * 分页
	 * @param pageNum 当前页 码
	 * @param pageSize 每页记录数
	 * @return
	 */
	public PageInfo findPage(Content content, int pageNum, int pageSize);
	
	/**
	 * 根据广告分类ID查询广告列表
	 * @param categoryId
	 * @return
	 */
	public List<Content> findByCategoryId(Long categoryId);
	/**
	 * @Author: Yancheng Guo
	 * @Date: 2019/3/7 17:09
	 * @Description: 更新商城公告
	*/
	public void updateAnnouncement(String announcement);

	public String findAnnouncement();

}

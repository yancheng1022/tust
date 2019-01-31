package com.tust.user.service;
import java.util.List;

import com.github.pagehelper.PageInfo;


import com.tust.pojo.User;

/**
 * 服务层接口
 * @author Administrator
 *
 */
public interface UserService {

	/**
	 * 返回全部列表
	 * @return
	 */
	public List<User> findAll();
	
	
	/**
	 * 返回分页列表
	 * @return
	 */
	public PageInfo findPage(int pageNum, int pageSize);
	
	
	/**
	 * 增加
	*/
	public void add(User user);
	
	
	/**
	 * 修改
	 */
	public void update(User user);
	

	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	public User findOne(Long id);
	
	
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
	public PageInfo findPage(User user, int pageNum, int pageSize);
	
	

	
}

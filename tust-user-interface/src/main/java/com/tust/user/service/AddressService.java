package com.tust.user.service;
import java.util.List;

import com.github.pagehelper.PageInfo;
import com.tust.pojo.Address;

/**
 * @Author: Yancheng Guo
 * @Date: 2019/1/29 16:03
 * @Description: 服务层接口
*/
public interface AddressService {

	/**
	 * @Author: Yancheng Guo
	 * @Date: 2019/1/29 16:03
	 * @Description: 返回全部列表
	*/
	public List<Address> findAll();
	
	
	/**
	 * @Author: Yancheng Guo
	 * @Date: 2019/1/29 16:04
	 * @Description: 返回分页列表
	*/
	public PageInfo findPage(int pageNum, int pageSize);
	
	
	/**
	 * @Author: Yancheng Guo
	 * @Date: 2019/1/29 16:04
	 * @Description: 增加
	*/
	public void add(Address address);
	
	
	/**
	 * @Author: Yancheng Guo
	 * @Date: 2019/1/29 16:04
	 * @Description: 修改
	*/
	public void update(Address address);
	

	/**
	 * @Author: Yancheng Guo
	 * @Date: 2019/1/29 16:04
	 * @Description: 根据id获取实体
	*/
	public Address findOne(Long id);
	
	
	/**
	 * @Author: Yancheng Guo
	 * @Date: 2019/1/29 16:05
	 * @Description: 批量删除
	*/
	public void delete(Long[] ids);

	/**
	 * @Author: Yancheng Guo
	 * @Date: 2019/1/29 16:05
	 * @Description: 分页
	*/
	public PageInfo findPage(Address address, int pageNum, int pageSize);
	
	public List<Address> findListByUserId(String userId);
	
}

package com.tust.order.service;
import com.alipay.api.AlipayApiException;
import com.github.pagehelper.PageInfo;
import com.tust.pojo.Order;

import java.util.List;

/**
 * 服务层接口
 * @author Administrator
 *
 */
public interface OrderService {

	/**
	 * @Author: Yancheng Guo
	 * @Date: 2019/1/29 20:12
	 * @Description: 返回全部列表
	*/
	public List<Order> findAll();
	
	
	/**
	 * @Author: Yancheng Guo
	 * @Date: 2019/1/29 20:13
	 * @Description: 返回分页列表
	*/
	public PageInfo findPage(int pageNum, int pageSize);
	
	
	/**
	 * @Author: Yancheng Guo
	 * @Date: 2019/1/29 20:13
	 * @Description: 增加
	*/
	public void add(Order order);
	
	
	/**
	 * @Author: Yancheng Guo
	 * @Date: 2019/1/29 20:13
	 * @Description: 修改
	*/
	public void update(Order order);
	

	/**
	 * @Author: Yancheng Guo
	 * @Date: 2019/1/29 20:14
	 * @Description: 根据id获取实体
	*/
	public Order findOne(Long id);
	
	
	/**
	 * @Author: Yancheng Guo
	 * @Date: 2019/1/29 20:14
	 * @Description: 批量删除
	*/
	public void delete(Long[] ids);

	/**
	 * @Author: Yancheng Guo
	 * @Date: 2019/1/29 20:14
	 * @Description: 分页
	*/
	public PageInfo findPage(Order order, int pageNum, int pageSize);

	/**
	 * @Author: Yancheng Guo
	 * @Date: 2019/3/19 16:05
	 * @Description: 跳转到支付宝支付页面
	*/
	public String goAliPay(String username) throws AlipayApiException;

	/**
	 * @Author: Yancheng Guo
	 * @Date: 2019/3/19 16:53
	 * @Description: 支付成功
	*/
	public void paySuccess(String out_trade_no,String trade_no);
}

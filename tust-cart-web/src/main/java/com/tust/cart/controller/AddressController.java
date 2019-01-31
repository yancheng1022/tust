package com.tust.cart.controller;
import java.util.List;

import com.github.pagehelper.PageInfo;
import com.tust.pojo.Address;
import com.tust.pojo.Result;
import com.tust.user.service.AddressService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.dubbo.config.annotation.Reference;

/**
 * @Author: Yancheng Guo
 * @Date: 2019/1/29 16:15
 * @Description: 地址控制层
*/
@RestController
@RequestMapping("/address")
public class AddressController {

	@Reference
	private AddressService addressService;
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<Address> findAll(){
		return addressService.findAll();
	}
	

	@RequestMapping("/findPage")
	public PageInfo findPage(int page, int rows){
		return addressService.findPage(page, rows);
	}
	
	/**
	 * 增加
	 * @param address
	 * @return
	 */
	@RequestMapping("/add")
	public Result add(@RequestBody Address address){
		try {
			addressService.add(address);
			return new Result(true, "增加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "增加失败");
		}
	}
	

	@RequestMapping("/update")
	public Result update(@RequestBody Address address){
		try {
			addressService.update(address);
			return new Result(true, "修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "修改失败");
		}
	}	
	

	@RequestMapping("/findOne")
	public Address findOne(Long id){
		return addressService.findOne(id);		
	}
	

	@RequestMapping("/delete")
	public Result delete(Long [] ids){
		try {
			addressService.delete(ids);
			return new Result(true, "删除成功"); 
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "删除失败");
		}
	}
	

	@RequestMapping("/search")
	public PageInfo search(@RequestBody Address address, int page, int rows  ){
		return addressService.findPage(address, page, rows);		
	}
	
	@RequestMapping("/findListByLoginUser")
	public List<Address> findListByLoginUser(){
		//获取登陆用户
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		return addressService.findListByUserId(username);		
	}
	
}

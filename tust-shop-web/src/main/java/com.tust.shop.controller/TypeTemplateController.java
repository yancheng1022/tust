package com.tust.shop.controller;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.tust.pojo.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.tust.pojo.TypeTemplate;
import com.tust.sellergoods.service.TypeTemplateService;


@RestController
@RequestMapping("/typeTemplate")
public class TypeTemplateController {

	@Reference
	private TypeTemplateService typeTemplateService;
	

	@RequestMapping("/findAll")
	public List<TypeTemplate> findAll(){
		return typeTemplateService.findAll();
	}
	
	

	@RequestMapping("/findPage")
	public PageInfo findPage(int page, int rows){
		return typeTemplateService.findPage(page, rows);
	}
	

	@RequestMapping("/add")
	public Result add(@RequestBody TypeTemplate typeTemplate){
		try {
			typeTemplateService.add(typeTemplate);
			return new Result(true, "增加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "增加失败");
		}
	}
	
	/**
	 * 修改
	 * @param typeTemplate
	 * @return
	 */
	@RequestMapping("/update")
	public Result update(@RequestBody TypeTemplate typeTemplate){
		try {
			typeTemplateService.update(typeTemplate);
			return new Result(true, "修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "修改失败");
		}
	}	
	

	@RequestMapping("/findOne")
	public TypeTemplate findOne(Long id){
		return typeTemplateService.findOne(id);		
	}
	

	@RequestMapping("/delete")
	public Result delete(Long [] ids){
		try {
			typeTemplateService.delete(ids);
			return new Result(true, "删除成功"); 
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "删除失败");
		}
	}


	@RequestMapping("/search")
	public PageInfo search(@RequestBody TypeTemplate typeTemplate, int page, int rows  ){
		return typeTemplateService.findPage(typeTemplate, page, rows);		
	}
	
	@RequestMapping("/findSpecList")
	public List<Map> findSpecList(Long id){
		return typeTemplateService.findSpecList(id);
	}
	
}

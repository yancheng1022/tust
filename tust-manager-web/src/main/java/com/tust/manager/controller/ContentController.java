package com.tust.manager.controller;
import java.util.List;

import com.github.pagehelper.PageInfo;
import com.tust.content.service.ContentService;
import com.tust.pojo.Content;
import com.tust.pojo.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.dubbo.config.annotation.Reference;

/**
 * controller
 * @author Administrator
 *
 */

@RequestMapping("/content")
@RestController
public class ContentController {

	@Reference
	private ContentService contentService;
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<Content> findAll(){
		return contentService.findAll();
	}
	
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findPage")
	public PageInfo findPage(int page, int rows){
		return contentService.findPage(page, rows);
	}
	
	/**
	 * 增加
	 * @param content
	 * @return
	 */
	@RequestMapping("/add")
	public Result add(@RequestBody Content content){
		try {
			contentService.add(content);
			return new Result(true, "增加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "增加失败");
		}
	}
	
	/**
	 * 修改
	 * @param content
	 * @return
	 */
	@RequestMapping("/update")
	public Result update(@RequestBody Content content){
		try {
			contentService.update(content);
			return new Result(true, "修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "修改失败");
		}
	}	
	
	/**
	 * 获取实体
	 * @param id
	 * @return
	 */
	@RequestMapping("/findOne")
	public Content findOne(Long id){
		return contentService.findOne(id);		
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delete")
	public Result delete(Long [] ids){
		try {
			contentService.delete(ids);
			return new Result(true, "删除成功"); 
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "删除失败");
		}
	}
	

	@RequestMapping("/search")
	public PageInfo search(@RequestBody Content content, int page, int rows  ){
		PageInfo page1 = contentService.findPage(content, page, rows);
		return page1;
	}

	/**
	 * @Author: Yancheng Guo
	 * @Date: 2019/3/7 17:23
	 * @Description: 更新站长公告
	*/
	@RequestMapping("/updateAnnouncement")
	public Result updateAnnouncement(@RequestBody String announcement){
		try {
			contentService.updateAnnouncement(announcement);
			return new Result(true, "更新成功");
		}catch (Exception e){
			e.printStackTrace();
			return new Result(false, "更新失败");
		}

	}

	/**
	 * @Author: Yancheng Guo
	 * @Date: 2019/3/7 18:07
	 * @Description: 查找网站公告
	*/
	@RequestMapping("/findAnnouncement")
	public String findAnnouncement(){
		String announcement = contentService.findAnnouncement();
		return announcement;
	}
	
}

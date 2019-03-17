package com.tust.content.service.impl;
import java.util.List;

import com.github.pagehelper.PageInfo;
import com.tust.content.service.ContentService;
import com.tust.mapper.AnnouncementMapper;
import com.tust.mapper.ContentMapper;
import com.tust.pojo.Content;
import com.tust.pojo.ContentExample;
import org.apache.zookeeper.data.Id;
import org.springframework.beans.factory.annotation.Autowired;


import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.data.redis.core.RedisTemplate;


/**
 * 服务实现层
 * @author Administrator
 *
 */
@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private ContentMapper contentMapper;
	@Autowired
	private RedisTemplate redisTemplate;
	@Autowired
	private AnnouncementMapper announcementMapper;
	/**
	 * 查询全部
	 */
	@Override
	public List<Content> findAll() {
		return contentMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageInfo findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<Content> contents = contentMapper.selectByExample(null);
		return new PageInfo(contents);
	}

	/**
	 * 增加
	 */
	@Override
	public void add(Content content) {
		contentMapper.insert(content);		
		//清除缓存
		redisTemplate.boundHashOps("content").delete(content.getCategoryId());
	}

	
	/**
	 * 修改
	 */
	@Override
	public void update(Content content){
		//查询原来的分组ID
		Long categoryId = contentMapper.selectByPrimaryKey(content.getId()).getCategoryId();
		//清除原分组的缓存
		redisTemplate.boundHashOps("content").delete(categoryId);
		
		contentMapper.updateByPrimaryKey(content);
		//清除现分组缓存
		if(categoryId.longValue()!=content.getCategoryId().longValue()){
			redisTemplate.boundHashOps("content").delete(content.getCategoryId());
		}
		
	}	
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public Content findOne(Long id){
		return contentMapper.selectByPrimaryKey(id);
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(Long[] ids) {
		for(Long id:ids){
			//清除缓存
			Long categoryId = contentMapper.selectByPrimaryKey(id).getCategoryId();			
			redisTemplate.boundHashOps("content").delete(categoryId);
			contentMapper.deleteByPrimaryKey(id);
		}		
	}
	
	
	@Override
	public PageInfo findPage(Content content, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		ContentExample example=new ContentExample();
		ContentExample.Criteria criteria = example.createCriteria();
		
		if(content!=null){			
						if(content.getTitle()!=null && content.getTitle().length()>0){
				criteria.andTitleLike("%"+content.getTitle()+"%");
			}
			if(content.getUrl()!=null && content.getUrl().length()>0){
				criteria.andUrlLike("%"+content.getUrl()+"%");
			}
			if(content.getPic()!=null && content.getPic().length()>0){
				criteria.andPicLike("%"+content.getPic()+"%");
			}
			if(content.getStatus()!=null && content.getStatus().length()>0){
				criteria.andStatusLike("%"+content.getStatus()+"%");
			}
	
		}

			List<Content> contents = contentMapper.selectByExample(example);
			return new PageInfo(contents);
	}


	@Override
	public List<Content> findByCategoryId(Long categoryId) {
		
		List<Content> list = (List<Content>) redisTemplate.boundHashOps("content").get(categoryId);

		if(list==null){
			System.out.println("从数据库中查询数据并放入缓存 ");
			ContentExample example=new ContentExample();
			ContentExample.Criteria criteria = example.createCriteria();
			criteria.andCategoryIdEqualTo(categoryId);//指定条件:分类ID
			criteria.andStatusEqualTo("1");//指定条件：有效
			example.setOrderByClause("sort_order");//排序
			list = contentMapper.selectByExample(example);
			redisTemplate.boundHashOps("content").put(categoryId, list);//放入缓存
		}else {
			System.out.println("从缓存中查询数据 ");
		}
				
		return list;
	}

	@Override
	public void updateAnnouncement(String announcement) {
		announcementMapper.updateAnnouncement(announcement);
	}

	@Override
	public String findAnnouncement() {
		return announcementMapper.findAnnouncement();
	}
}

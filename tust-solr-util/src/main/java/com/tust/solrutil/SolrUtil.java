package com.tust.solrutil;

import java.util.List;
import java.util.Map;

import com.tust.mapper.ItemMapper;
import com.tust.pojo.Item;
import com.tust.pojo.ItemExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;


@Component
public class SolrUtil {

	@Autowired
	private ItemMapper itemMapper;
	
	@Autowired
	private SolrTemplate solrTemplate;
	
	public void importItemData(){
		
		ItemExample example=new ItemExample();
		ItemExample.Criteria criteria = example.createCriteria();
		criteria.andStatusEqualTo("1");//审核通过的才导入的
		List<Item> itemList = itemMapper.selectByExample(example);
		
		System.out.println("---商品列表---");
		for(Item item:itemList){
			System.out.println(item.getId()+" "+ item.getTitle()+ " "+item.getPrice());	
			Map specMap = JSON.parseObject(item.getSpec(), Map.class);//从数据库中提取规格json字符串转换为map
			item.setSpecMap(specMap);
		}
		
		solrTemplate.saveBeans(itemList);
		solrTemplate.commit();
		
		System.out.println("---结束---");
	}
	
	public static void main(String[] args) {
		
		ApplicationContext context=new ClassPathXmlApplicationContext("classpath*:spring/applicationContext*.xml");
		SolrUtil solrUtil=  (SolrUtil) context.getBean("solrUtil");
		solrUtil.importItemData();
		
	}
	
	
}

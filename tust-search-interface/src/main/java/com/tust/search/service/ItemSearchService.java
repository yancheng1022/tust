package com.tust.search.service;

import java.util.List;
import java.util.Map;

public interface ItemSearchService {

	
	/**
	 * @Author: Yancheng Guo
	 * @Date: 2019/1/22 10:44
	 * @Description: 搜索方法
	*/
	public Map search(Map searchMap);

	public void importList(List list);

	/**
	 * @Author: Yancheng Guo
	 * @Date: 2019/1/23 17:27
	 * @Description: 删除商品列表
	*/
	public void deleteByGoodsIds(List GoodsIds);
	
}

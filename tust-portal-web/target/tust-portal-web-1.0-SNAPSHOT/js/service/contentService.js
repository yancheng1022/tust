app.service('contentService' ,function($http){
	
	//根据广告分类ID查询广告
	this.findByCategoryId=function(categoryId){
		return $http.get('content/findByCategoryId.do?categoryId='+categoryId);
	}

	//网站公告
	this.findAnnouncement=function(){
		return $http.post('../content/findAnnouncement.do');
	}

	//新商家
	this.findNewSeller=function(){
		return $http.post('../seller/findNewSeller.do');
	}

	//查询厂家商品
	this.findMallGoods=function(){
		return $http.post('../goods/findMallGoods.do');
	}
});
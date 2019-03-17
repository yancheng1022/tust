app.controller("contentController",function($scope,contentService){
	$scope.contentList = [];
	// 根据分类ID查询广告的方法:
	$scope.findByCategoryId = function(categoryId){
		contentService.findByCategoryId(categoryId).success(function(response){
			$scope.contentList[categoryId] = response;
		});
	}

	//搜索  （传递参数）
	$scope.search=function(){
		location.href="http://localhost:9104/search.html#?keywords="+$scope.keywords;
	}
	//查询网站公告
	$scope.findAnnouncement=function(){
		contentService.findAnnouncement().success(
			function(response){
				$scope.announcement = response;
			}
		);
	}

	//查询新公司
	$scope.findNewSeller=function(){
		contentService.findNewSeller().success(
			function(response){
				console.log(response);
				$scope.seller = response;
			}
		);
	}

	//查询网站商品
	$scope.findMallGoods=function(){
		contentService.findMallGoods().success(
			function(response){
				console.log(response);
				$scope.mallGoods = response;
			}
		);
	}
});
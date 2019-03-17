//品牌控制层
app.controller('indexController' ,function($scope,loginService){

    //当前用户信息
    $scope.showLogin = function () {
        loginService.loginName().success(
            function(response){
                $scope.name = response.name;
            }
        );
    }

});
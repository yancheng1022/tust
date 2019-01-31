package com.tust.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.tust.pojo.Result;
import com.tust.pojo.Seller;
import com.tust.sellergoods.service.SellerService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/seller")
public class SellerController {

    @Reference
    private SellerService sellerService;


    @RequestMapping("/findAll")
    public List<Seller> findAll(){
        return sellerService.findAll();
    }



    @RequestMapping("/findPage")
    public PageInfo findPage(int page, int rows){
        return sellerService.findPage(page, rows);
    }


    @RequestMapping("/add")
    public Result add(@RequestBody Seller seller){
        //密码加密
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode(seller.getPassword());
        seller.setPassword(password);
        try {
            sellerService.add(seller);
            return new Result(true, "增加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "增加失败");
        }
    }


    @RequestMapping("/update")
    public Result update(@RequestBody Seller seller){
        try {
            sellerService.update(seller);
            return new Result(true, "修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "修改失败");
        }
    }


    @RequestMapping("/findOne")
    public Seller findOne(String id){
        return sellerService.findOne(id);
    }


    @RequestMapping("/delete")
    public Result delete(String [] ids){
        try {
            sellerService.delete(ids);
            return new Result(true, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除失败");
        }
    }


    @RequestMapping("/search")
    public PageInfo search(@RequestBody Seller seller, int page, int rows  ){
        return sellerService.search(seller, page, rows);
    }

    @RequestMapping("/updateStatus")
    public Result updateStatus(String sellerId,String status){
        try {
            sellerService.updateStatus(sellerId, status);
            return new Result(true, "成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "失败");
        }

    }

}


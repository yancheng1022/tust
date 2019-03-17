package com.tust.portal.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.tust.pojo.Seller;
import com.tust.sellergoods.service.SellerService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/seller")
public class SellerController {

    @Reference
    private SellerService sellerService;

    @RequestMapping("/findNewSeller")
    public List<Seller> findNewSeller(){
        List<Seller> sellerList = sellerService.findNewSeller();
        return sellerList;
    }
}

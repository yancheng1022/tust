package com.tust.manager.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.tust.VO.SpecificationVO;
import com.tust.pojo.Result;
import com.tust.pojo.Specification;
import com.tust.sellergoods.service.SpecificationService;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.Calendar;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/specification")
public class SpecificationController {
    @Reference
    private SpecificationService specificationService;

    @RequestMapping("/findAll")
    public List<Specification>findAll(){
        return specificationService.findAll();
    }

    @RequestMapping("/add")
    public Result add(@RequestBody SpecificationVO specificationVO){
        try {
            specificationService.add(specificationVO);
            return new Result(true, "增加成功");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, "增加失败");
        }
    }

    @RequestMapping("/update")
    public Result update(@RequestBody SpecificationVO specificationVO){
        try {
            specificationService.update(specificationVO);
            return new Result(true, "更新成功");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, "更新失败");
        }
    }

    @RequestMapping("/findOne")
    public SpecificationVO findOne(Long id){
        return specificationService.findOne(id);
    }

    @RequestMapping("/delete")
    public Result delete(Long[] ids){
        try {
            specificationService.delete(ids);
            return new Result(true, "删除成功");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, "删除失败");
        }
    }

    @RequestMapping("/search")
    public PageInfo search(@RequestBody Specification specification,int page,int rows){
        PageInfo result = specificationService.search(specification, page, rows);
        return result;
    }

    @RequestMapping("/selectOptionList")
    public List<Map> selectOptionList(){
        return specificationService.selectOptionList();
    }

}

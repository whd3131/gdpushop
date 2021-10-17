package com.gdpu.controller;


import com.gdpu.entity.Business;
import com.gdpu.service.BusinessService;
import com.gdpu.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author whd
 * @since 2021-10-15
 */
@RestController
@RequestMapping("/business")
@CrossOrigin
public class BusinessController {

    @Autowired
    private BusinessService businessService;

    //获得商家的信息，根据商品的ID
    @GetMapping("/getBusinessByGoodsId/{goodsId}")
    public R getBusinessByGoodsId(@PathVariable String goodsId){
        Business business = businessService.getBusinessByGoodsId(goodsId);
        return R.ok().data("business",business);
    }

    //添加商家与商品的关联关系
    @PostMapping("/addBusinessGoods")
    public R addBusinessGoods(@RequestBody String bgInfo){
        boolean res = businessService.addBusinessGoods(bgInfo);
        return res? R.ok():R.error();
    }

    //添加一个商家
    @PostMapping("/addBusiness")
    public R addBusiness(@RequestBody String businessInfo){
        boolean res = businessService.addBusiness(businessInfo);
        return res? R.ok():R.error();
    }

    //获取所有商家
    @GetMapping("/getAllBusiness")
    public R getAllBusiness(){
        List<Business> businessList = businessService.list();
        return R.ok().data("businessList",businessList);
    }
}


package com.gdpu.controller;


import com.gdpu.entity.addressVo.OneAddressVo;
import com.gdpu.service.BaseAddressService;
import com.gdpu.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author whd
 * @since 2021-10-14
 */
@RestController
@RequestMapping("/base-address")
@CrossOrigin
public class BaseAddressController {

    @Autowired
    BaseAddressService baseAddressService;

    // 获得所有的城市地址
    @GetMapping("/getAllAddress")
    public R getAllAddress(){
        List<OneAddressVo> cityList = baseAddressService.getAllAddress();
        return R.ok().data("cityList",cityList);
    }

}


package com.gdpu.controller;


import com.gdpu.entity.Advertisement;
import com.gdpu.service.AdvertisementService;
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
 * @since 2021-10-11
 */
@RestController
@RequestMapping("/ad")
@CrossOrigin
public class AdvertisementController {

    @Autowired
    AdvertisementService advertisementService;

    @GetMapping("/getAd")
    public R getAd(){
        List<Advertisement> adList = advertisementService.list();
        return R.ok().data("adList",adList);
    }
}


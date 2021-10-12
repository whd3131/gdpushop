package com.gdpu.controller;


import com.gdpu.entity.vo.PayVo;
import com.gdpu.service.CartOrderService;
import com.gdpu.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 购物车-订单-关系表 前端控制器
 * </p>
 *
 * @author whd
 * @since 2021-10-09
 */
@RestController
@RequestMapping("/cartOrder")
@CrossOrigin
public class CartOrderController {

    @Autowired
    CartOrderService cartOrderService;

    @PostMapping("/getPayInfo")
    public R getPayInfo(@RequestBody String payInfo){
        List<PayVo> payVoList = cartOrderService.getPayInfo(payInfo);
        return R.ok().data("payVoList",payVoList);
    }
}


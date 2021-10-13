package com.gdpu.controller;


import com.gdpu.entity.OrderInfo;
import com.gdpu.service.OrderInfoService;
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
 * @since 2021-10-09
 */
@RestController
@RequestMapping("/order")
@CrossOrigin
public class OrderInfoController {

    @Autowired
    private OrderInfoService orderInfoService;

    //获取所有订单
    @GetMapping("/getAllOrder")
    public R getAllOrder(){
        List<OrderInfo> orderList = orderInfoService.list();
        return R.ok().data("orderList",orderList);
    }

    //修改订单状态为已完成
    @PostMapping("/updateStatus")
    public R updateStatus(@RequestBody String orderId){
        boolean res = orderInfoService.updateStatus(orderId);
        return res?R.ok():R.error();
    }

    //删除一条订单
    @PostMapping("/delete")
    public R deleteOrder(@RequestBody String orderId){
        boolean res = orderInfoService.deleteOrder(orderId);
        return res?R.ok():R.error();
    }

    // 添加一条订单记录
    @PostMapping("/addOrder")
    public R addOrder(@RequestBody String order){
        String order_id = orderInfoService.addOrder(order);
        if(order_id!=null){
            return R.ok().data("orderId",order_id);
        }else{
            return R.error();
        }
    }
}


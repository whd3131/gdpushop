package com.gdpu.rabbitmq.consumer;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.gdpu.entity.OrderInfo;
import com.gdpu.service.OrderInfoService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 订单死信队列消费者
 * @author:whd
 * @createTime: 2021/10/24
 */

@Component //死信队列
public class OrderDlxConsumer {

    @Autowired
    OrderInfoService orderInfoService;

    /**
     * 监听我们的死信队列
     */
    @RabbitListener(queues = "gdpu_order_dlx_queue")
    public void orderConsumer(String orderSign) {
        System.out.println("死信队列获取消息:" + orderSign);
        if (StringUtils.isEmpty(orderSign)) {
            return;
        }
        //根据id查询
        QueryWrapper<OrderInfo> orderInfoQueryWrapper = new QueryWrapper<>();
        orderInfoQueryWrapper.eq("order_sign",orderSign);
        OrderInfo one = orderInfoService.getOne(orderInfoQueryWrapper);

        //如果这个订单的状态为1，表明已完成，则返回
        if (one == null) {
           return;
        }
        if(one.getOrderStatus() == 1){
            return;
        }


        //获取状态
        Integer status = one.getOrderStatus();

        boolean res = false;
        if(0==status){
            //订单未支付，而且在死信队列中，关闭订单，设置为2
            UpdateWrapper<OrderInfo> updateWrapper = new UpdateWrapper<>();
            updateWrapper.set("order_status",2);
            updateWrapper.eq("order_sign",orderSign);
            res = orderInfoService.update(updateWrapper);
        }

        System.out.println("更新状态："+res);
    }
}

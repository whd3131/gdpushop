package com.gdpu.controller;

import com.gdpu.entity.OrderInfo;
import com.gdpu.service.OrderInfoService;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author:whd
 * @createTime: 2021/10/24
 * 测试rabbitmq
 */
@RestController
public class TestController {
    @Autowired
    private OrderInfoService orderInfoService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${gdpu.order.exchange}")
    private String orderExchange; //订单交换机

    @Value("${gdpu.order.routingKey}")
    private String orderRoutingKey; //订单路由key

    @GetMapping("/addOrder")
    public String addOrder(){
//        String orderId=System.currentTimeMillis()+"";
        OrderInfo orderInfo = new OrderInfo();
        String oid = "999";
        orderInfo.setOrderId("999");
        orderInfo.setOrderStatus(0);
        orderInfo.setOrderPrice(999);
        orderInfo.setUserId("123");
        orderInfo.setOrderSign("订单10s过期");

        //订单入库
        boolean res = orderInfoService.save(orderInfo);

        if(!res){
            return "fail";
        }

        //rabbit投递消息
        rabbitTemplate.convertAndSend(orderExchange,orderRoutingKey,oid,messagePostProcessor());
        return "success";
    }

    //处理待发送消息
    private MessagePostProcessor messagePostProcessor(){
        return  new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                //设置有效期30分钟
                //message.getMessageProperties().setExpiration("1800000");
                message.getMessageProperties().setExpiration("10000");
                return message;
            }
        };
    }
}

package com.gdpu.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 死信MQ配置类
 * @author:whd
 * @createTime: 2021/10/24
 */

@Component
public class DeadLetterMQConfig {
    /**
     * 订单交换机
     */
    @Value("${gdpu.order.exchange}")
    private String orderExchange;

    /**
     * 订单队列
     */
    @Value("${gdpu.order.queue}")
    private String orderQueue;

    /**
     * 订单路由key
     */
    @Value("${gdpu.order.routingKey}")
    private String orderRoutingKey;

    /**
     * 死信交换机
     */
    @Value("${gdpu.dlx.exchange}")
    private String dlxExchange;
    /**
     * 死信队列
     */
    @Value("${gdpu.dlx.queue}")
    private String dlxQueue;
    /**
     * 死信路由
     */
    @Value("${gdpu.dlx.routingKey}")
    private String dlxRoutingKey;
    /**
     * 声明死信交换机
     */
    @Bean("dlxExchange")
    public DirectExchange dlxExchange() {
        return new DirectExchange(dlxExchange);
    }
    /**
     * 声明死信队列
     */
    @Bean("dlxQueue")
    public Queue dlxQueue() {
        return new Queue(dlxQueue);
    }
    /**
     * 声明订单业务交换机
     */
    @Bean("orderExchange")
    public DirectExchange orderExchange() {
        return new DirectExchange(orderExchange);
    }
    /**
     * 声明订单队列 核心操作一
     */
    @Bean("orderQueue")
    public Queue orderQueue() {
        Map<String, Object> arguments = new HashMap<>(2);
        // 绑定我们的死信交换机
        arguments.put("x-dead-letter-exchange", dlxExchange);
        // 绑定我们的路由key
        arguments.put("x-dead-letter-routing-key", dlxRoutingKey);
        return new Queue(orderQueue, true, false, false, arguments);
    }
    /**
     * 绑定订单队列到订单交换机
     */
    @Bean
    public Binding orderBinding(@Qualifier("orderQueue") Queue orderQueue,
                                @Qualifier("orderExchange")DirectExchange orderExchange) {
        return BindingBuilder
                .bind(orderQueue)
                .to(orderExchange)
                .with(orderRoutingKey);
    }
    /**
     * 绑定死信队列到死信交换机
     */
    @Bean
    public Binding binding(@Qualifier("dlxQueue") Queue dlxQueue,
                           @Qualifier("dlxExchange")DirectExchange dlxExchange) {
        return BindingBuilder
                .bind(dlxQueue)
                .to(dlxExchange)
                .with(dlxRoutingKey);
    }
}

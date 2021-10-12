package com.gdpu.service;

import com.gdpu.entity.OrderInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author whd
 * @since 2021-10-09
 */
public interface OrderInfoService extends IService<OrderInfo> {

    // 添加一条订单记录
    String addOrder(String order);

    // 删除一条记录
    boolean deleteOrder(String orderId);

    //修改订单状态为已完成
    boolean updateStatus(String orderId);
}

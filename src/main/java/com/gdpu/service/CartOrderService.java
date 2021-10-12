package com.gdpu.service;

import com.gdpu.entity.CartOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gdpu.entity.vo.PayVo;

import java.util.List;

/**
 * <p>
 * 购物车-订单-关系表 服务类
 * </p>
 *
 * @author whd
 * @since 2021-10-09
 */
public interface CartOrderService extends IService<CartOrder> {

    // 返回前端所需的支付VO
    List<PayVo> getPayInfo(String payInfo);

    // 根据orderId 得到cartId
    List<String> getCartIdByOrderId(String orderId);

}

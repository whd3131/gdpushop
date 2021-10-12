package com.gdpu.mapper;

import com.gdpu.entity.CartOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gdpu.entity.vo.PayVo;

import java.util.List;

/**
 * <p>
 * 购物车-订单-关系表 Mapper 接口
 * </p>
 *
 * @author whd
 * @since 2021-10-09
 */
public interface CartOrderMapper extends BaseMapper<CartOrder> {
    List<PayVo> getPayInfo(String orderId);
    List<String> getCartIdByOrderId(String orderId);
}

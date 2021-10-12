package com.gdpu.service.impl;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.gdpu.entity.CartOrder;
import com.gdpu.entity.vo.PayVo;
import com.gdpu.mapper.CartOrderMapper;
import com.gdpu.service.CartOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 购物车-订单-关系表 服务实现类
 * </p>
 *
 * @author whd
 * @since 2021-10-09
 */
@Service
public class CartOrderServiceImpl extends ServiceImpl<CartOrderMapper, CartOrder> implements CartOrderService {

    // 返回前端所需的支付VO
    @Override
    public List<PayVo> getPayInfo(String payInfo) {
        JSONObject jsonObject = JSONUtil.parseObj(payInfo);
        String orderId = jsonObject.getStr("orderId");
        List<PayVo> pvoList = baseMapper.getPayInfo(orderId);
        return pvoList;
    }

    @Override
    public List<String> getCartIdByOrderId(String orderId) {
        List<String> cartIdList = baseMapper.getCartIdByOrderId(orderId);
        return cartIdList;
    }
}

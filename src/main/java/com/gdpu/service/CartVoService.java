package com.gdpu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gdpu.entity.Cart;
import com.gdpu.entity.vo.CartVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author whd
 * @since 2021-10-08
 */
public interface CartVoService extends IService<CartVo> {

    //获得用户购物车信息
    List<CartVo> getUserCart(String userId);
}

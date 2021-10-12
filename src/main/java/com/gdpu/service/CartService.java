package com.gdpu.service;

import com.gdpu.entity.Cart;
import com.baomidou.mybatisplus.extension.service.IService;
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
public interface CartService extends IService<Cart> {

    //保存到购物车
    boolean saveCart(String cart);


    //修改购物车里商品的数量
    boolean updateNums(String updateNums);

    //删除购物车的商品
    boolean deleteById(String id);
}

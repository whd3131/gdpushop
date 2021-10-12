package com.gdpu.service.impl;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdpu.entity.Cart;
import com.gdpu.entity.vo.CartVo;
import com.gdpu.mapper.CartMapper;
import com.gdpu.mapper.CartVoMapper;
import com.gdpu.service.CartService;
import com.gdpu.service.CartVoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author whd
 * @since 2021-10-08
 */
@Service
public class CartVoServiceImpl extends ServiceImpl<CartVoMapper, CartVo> implements CartVoService {

    // 获得用户购物车信息
    @Override
    public List<CartVo> getUserCart(String userId) {
        List<CartVo> cartVoList = baseMapper.getUserCart(userId);
        return cartVoList;
    }
}

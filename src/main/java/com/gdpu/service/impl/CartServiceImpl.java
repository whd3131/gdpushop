package com.gdpu.service.impl;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.gdpu.entity.Cart;
import com.gdpu.entity.vo.CartVo;
import com.gdpu.mapper.CartMapper;
import com.gdpu.service.CartService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements CartService {

    //获得用户购物车信息
    @Override
    public List<CartVo> getUserCart(String userId) {
        return baseMapper.getUserCart(userId);
    }

    // 保存到购物车
    @Override
    public boolean saveCart(String cart) {
        JSONObject jsonObject = JSONUtil.parseObj(cart);
        String goodsId = jsonObject.getStr("goodsId");
        String num = jsonObject.getStr("num");
        String userId = jsonObject.getStr("userId");

        Cart ca = new Cart();
        ca.setGoodsId(goodsId);
        ca.setNums(Integer.valueOf(num));
        ca.setUserId(userId);

        int res = baseMapper.insert(ca);
        return res > 0;
    }

    // 修改购物车里商品的数量
    @Override
    public boolean updateNums(String updateNums) {
        JSONObject jsonObject = JSONUtil.parseObj(updateNums);
        String id = jsonObject.getStr("id");
        String nums = jsonObject.getStr("nums");

        Cart ca = new Cart();
        ca.setId(id);
        ca.setNums(Integer.valueOf(nums));
        int res = baseMapper.updateById(ca);
        return res>0;

    }

    //删除购物车的商品
    @Override
    public boolean deleteById(String id) {
        JSONObject jsonObject = JSONUtil.parseObj(id);
        String cartId = jsonObject.getStr("id");
        int res = baseMapper.deleteById(cartId);
        return res>0;
    }


}

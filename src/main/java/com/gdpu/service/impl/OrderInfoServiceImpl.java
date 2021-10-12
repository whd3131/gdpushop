package com.gdpu.service.impl;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gdpu.entity.*;
import com.gdpu.mapper.OrderInfoMapper;
import com.gdpu.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author whd
 * @since 2021-10-09
 */
@Service
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements OrderInfoService {

    @Autowired
    CartOrderService cartOrderService;
    @Autowired
    OrderInfoService orderInfoService;
    @Autowired
    CartService cartService;
    @Autowired
    GoodsService goodsService;
    @Autowired
    GoodsOrderService goodsOrderService;


    // 添加一条订单记录
    @Override
    public String addOrder(String order) {
        JSONObject jsonObject = JSONUtil.parseObj(order);
        String userId = jsonObject.getStr("userId");
        String sum = jsonObject.getStr("sum");
        JSONArray jsonArray = jsonObject.getJSONArray("cartIds");

        OrderInfo or = new OrderInfo();
        or.setOrderPrice(Integer.valueOf(sum));
        or.setUserId(userId);
        or.setOrderStatus(0);

        int res = baseMapper.insert(or);

        if(res>0){
            //返回订单ID
            QueryWrapper<OrderInfo> wrapper = new QueryWrapper<>();
            wrapper.eq("user_id",userId);
            wrapper.eq("order_price",sum);
            wrapper.eq("order_status",0);
            wrapper.eq("create_time",new Date());
            OrderInfo orderInfo = baseMapper.selectOne(wrapper);
            if(orderInfo!=null){

                //根据cartId和orderId去向cart_order表中添加数据
                for (Object o : jsonArray) {
                    String cartId = o.toString();
                    String orderId = orderInfo.getOrderId();
                    CartOrder co = new CartOrder();
                    co.setCartId(cartId);
                    co.setOrderId(orderId);
                    boolean saveCo = cartOrderService.save(co);
                    if(!saveCo){
                        throw new RuntimeException("添加一条Cart-Order数据失败");
                    }
                }

                return orderInfo.getOrderId();
            }
        }

        return null;
    }

    // 删除一条订单
    @Override
    public boolean deleteOrder(String orderId) {
        //从order_info 和 cart_order表中删除这个订单
        JSONObject jsonObject = JSONUtil.parseObj(orderId);
        String oid = jsonObject.getStr("orderId");

        //删除order_info中的订单(逻辑删除，方便日后查看自己取消的订单）
        int oi_res = baseMapper.deleteById(oid);
        //删除cart_order中订单
        QueryWrapper<CartOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("order_id",oid);
        boolean co_res = cartOrderService.remove(wrapper);


        return oi_res>0 && co_res;
    }


    //修改订单状态为已完成
    @Override
    public boolean updateStatus(String orderId) {

        boolean flag=false;
        boolean flag0=false;
        boolean flag1,flag2,flag3;

        JSONObject jsonObject = JSONUtil.parseObj(orderId);
        String oid = jsonObject.getStr("orderId");

        //删除cart_order表相关信息之前：要修改商品的购买数量
        //获取顺序 order_id -> cart_id -> cart -> goods_id -> goods -> count_buy
        QueryWrapper<CartOrder> cartOrderQueryWrapper1 = new QueryWrapper<>();
        cartOrderQueryWrapper1.eq("order_id",oid);
        List<String> cartIdList = cartOrderService.getCartIdByOrderId(oid);

        // 遍历是处理买了多个不同商品的情况
        for (String cartId : cartIdList) {
            Cart cart = cartService.getById(cartId);
            String goodsId = cart.getGoodsId();
            Integer goodsNums = cart.getNums();

            //向goods_order表中加入数据
            GoodsOrder go = new GoodsOrder();
            go.setOrderId(oid);
            go.setGoodsId(goodsId);
            go.setGoodsNums(goodsNums);
            flag = goodsOrderService.save(go);

            //修改购买数量
            Goods goods = goodsService.getById(goodsId);
            Integer countBuy = goods.getCountBuy();
            Goods newGoods = new Goods();
            newGoods.setGoodsId(goodsId);
            newGoods.setCountBuy(countBuy+1);
            flag0 = goodsService.updateById(newGoods);
        }




        //删除cart_order表相关信息
        QueryWrapper<CartOrder> cartOrderQueryWrapper2 = new QueryWrapper<>();
        cartOrderQueryWrapper2.eq("order_id",oid);
        flag1 = cartOrderService.remove(cartOrderQueryWrapper2);

        OrderInfo oi = new OrderInfo();
        oi.setOrderId(oid);
        oi.setOrderStatus(1);
        flag2 = orderInfoService.updateById(oi);

        // 清空该用户的购物车
        OrderInfo orderInfo = orderInfoService.getById(oid);
        String userId = orderInfo.getUserId();
        QueryWrapper<Cart> cartQueryWrapper = new QueryWrapper<>();
        cartQueryWrapper.eq("user_id",userId);
        flag3 = cartService.remove(cartQueryWrapper);


        return flag && flag0 && flag1 && flag2 && flag3;
    }
}

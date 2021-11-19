package com.gdpu.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gdpu.entity.Goods;
import com.gdpu.entity.GoodsOrder;
import com.gdpu.entity.OrderInfo;
import com.gdpu.entity.User;
import com.gdpu.entity.vo.LoginVo;
import com.gdpu.entity.vo.UserBuyVo;
import com.gdpu.mapper.UserMapper;
import com.gdpu.service.GoodsOrderService;
import com.gdpu.service.GoodsService;
import com.gdpu.service.OrderInfoService;
import com.gdpu.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdpu.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author whd
 * @since 2021-10-08
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    OrderInfoService orderInfoService;
    @Autowired
    GoodsOrderService goodsOrderService;
    @Autowired
    GoodsService goodsService;

    // 用户登录方法
    @Override
    public String login(LoginVo loginVo) {
        String username = loginVo.getUsername();
        String password = loginVo.getPassword();

        // 判空处理
        if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
            throw new RuntimeException("error");
        }

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        User user = baseMapper.selectOne(wrapper);
        if(null == user){
            throw new RuntimeException("查不到此用户");
        }

        //校验密码
        if(!password.equals(user.getPassword())){
            throw new RuntimeException("密码错误");
        }

        //Token信息
        String userId = user.getUserId();
        String name = user.getName();
        Map<String,String> map = new HashMap<>();
        map.put("userId",userId);
        map.put("name",name);
        String token = JwtUtil.getToken(map);
        return token;

    }

    //获取用户订单
    @Override
    public List<UserBuyVo> getUserOrder(String userId) {
        List<UserBuyVo> userBuyVoList = new ArrayList<>();

        //1. 根据用户ID查询order_info表得到订单ID、订单创建时间、订单总价、交易状态的信息的【列表】
        QueryWrapper<OrderInfo> orderInfoQueryWrapper = new QueryWrapper<>();
        orderInfoQueryWrapper.eq("user_id",userId);
        List<OrderInfo> orderInfoList = orderInfoService.list(orderInfoQueryWrapper);
        // * 遍历订单
        for (OrderInfo orderInfo : orderInfoList) {
            UserBuyVo userBuyVo = new UserBuyVo();

            String orderId = orderInfo.getOrderId();
            Date createTime = orderInfo.getCreateTime();
            //格式化创建时间
            String formatCreateDate = DateUtil.format(createTime, "yyyy年MM月dd日 HH时mm分ss秒");
            Integer orderPrice = orderInfo.getOrderPrice();
            Integer orderStatus = orderInfo.getOrderStatus();

            userBuyVo.setOrderId(orderId);
            userBuyVo.setCreateTime(formatCreateDate);
            userBuyVo.setOrderPrice(orderPrice);
            userBuyVo.setOrderStatus(orderStatus);

            //2. 根据订单ID查询goods_order表得到goods_id、购买数量的信息的【列表】
            QueryWrapper<GoodsOrder> goodsOrderQueryWrapper = new QueryWrapper<>();
            goodsOrderQueryWrapper.eq("order_id",orderId);
            List<GoodsOrder> goodsOrderList = goodsOrderService.list(goodsOrderQueryWrapper);

            List<Goods> goodsList = new ArrayList<>();
            List<Integer> goodsNumArray = new ArrayList<>();

            // * 遍历单个订单下的商品信息
            for (GoodsOrder goodsOrder : goodsOrderList) {
                Integer goodsNums = goodsOrder.getGoodsNums();
                goodsNumArray.add(goodsNums);

                //根据goodsId去得到商品的信息
                String goodsId = goodsOrder.getGoodsId();
                Goods goods = goodsService.getById(goodsId);
                goodsList.add(goods);
            }

            userBuyVo.setGoodsList(goodsList);
            userBuyVo.setGoodsNumsArray(goodsNumArray);
            userBuyVoList.add(userBuyVo);
        }


        return userBuyVoList;
    }
}

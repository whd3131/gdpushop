package com.gdpu.service;

import com.gdpu.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gdpu.entity.vo.LoginVo;
import com.gdpu.entity.vo.UserBuyVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author whd
 * @since 2021-10-08
 */
public interface UserService extends IService<User> {

    // 用户登录方法
    String login(LoginVo loginVo);

    //获取用户订单
    List<UserBuyVo> getUserOrder(String userId);
}

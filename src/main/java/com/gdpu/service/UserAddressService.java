package com.gdpu.service;

import com.gdpu.entity.UserAddress;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author whd
 * @since 2021-10-15
 */
public interface UserAddressService extends IService<UserAddress> {

    //保存地址
    boolean saveAddress(String addressInfo,String userId);

    //查询该用户所有的地址信息
    List<UserAddress> getAllAddressByUserId(String userId);

    //编辑地址
    boolean editAddress(String userId, String addressInfo);

}

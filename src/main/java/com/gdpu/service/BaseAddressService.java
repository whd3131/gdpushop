package com.gdpu.service;

import com.gdpu.entity.BaseAddress;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gdpu.entity.addressVo.OneAddressVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author whd
 * @since 2021-10-14
 */
public interface BaseAddressService extends IService<BaseAddress> {

    // 获得所有的城市地址
    List<OneAddressVo> getAllAddress();
}

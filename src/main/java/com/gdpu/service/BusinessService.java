package com.gdpu.service;

import com.gdpu.entity.Business;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author whd
 * @since 2021-10-15
 */
public interface BusinessService extends IService<Business> {

    //添加一个商家
    boolean addBusiness(String businessInfo);

    //添加商家与商品的关联关系
    boolean addBusinessGoods(String bgInfo);

    //获得商家的信息，根据商品的ID
    Business getBusinessByGoodsId(String goodsId);
}

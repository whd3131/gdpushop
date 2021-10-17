package com.gdpu.mapper;

import com.gdpu.entity.Business;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author whd
 * @since 2021-10-15
 */
public interface BusinessMapper extends BaseMapper<Business> {

    //获得商家的信息，根据商品的ID
    Business getBusinessByGoodsId(String goodsId);
}

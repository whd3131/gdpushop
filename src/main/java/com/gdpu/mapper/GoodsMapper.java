package com.gdpu.mapper;

import com.gdpu.entity.Goods;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author whd
 * @since 2021-10-08
 */
public interface GoodsMapper extends BaseMapper<Goods> {
    //得到所有未分配商家的商品列表
    List<Goods> getGoodsNonDistribution();

    //分页查询商品信息（关联商家表）
    List<Goods> getSearchGoods(String title,
                               String business,
                               Integer sortType,
                               Integer priceRange1,
                               Integer priceRange2,
                               long begin,
                               long pageSize);
}

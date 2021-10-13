package com.gdpu.service;

import com.gdpu.entity.Goods;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author whd
 * @since 2021-10-08
 */
public interface GoodsService extends IService<Goods> {

    //根据商品分类的值查询商品
    List<Goods> getGoodsByCatValue(String catValue);

    //添加一条商品
    boolean addGoods(String goodsInfo);

    // 获取所有商品
    List<Goods> getAllGoods();

}

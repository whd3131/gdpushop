package com.gdpu.service;

import cn.hutool.json.JSONObject;
import com.gdpu.entity.Goods;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gdpu.entity.vo.GoodsSearchVo;

import java.util.List;
import java.util.Map;

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

    //用户商品浏览历史（Redis缓存）
    boolean saveGoodsHistory(String userId,String goodsId);

    //获取用户浏览历史
    List<Goods> getGoodsHistory(String userId);

    //得到所有未分配商家的商品列表
    List<Goods> getGoodsNonDistribution();

    //分页得到商品列表
    Map<String,Object> getPageGoods(long currentPage, long pageSize);

    //分页查询得到商品列表
    Map<String, Object> getSearchGoods(long currentPage, long pageSize, String goodsSearchVo);
}

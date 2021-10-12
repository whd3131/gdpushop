package com.gdpu.service.impl;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gdpu.entity.Category;
import com.gdpu.entity.Goods;
import com.gdpu.mapper.GoodsMapper;
import com.gdpu.service.CategoryService;
import com.gdpu.service.GoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author whd
 * @since 2021-10-08
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {
    @Autowired
    CategoryService categoryService;
    //根据商品分类的值查询商品
    @Override
    public List<Goods> getGoodsByCatValue(String catValue) {

        QueryWrapper<Category> categoryQueryWrapper = new QueryWrapper<>();
        categoryQueryWrapper.eq("cat_value",catValue);
        Category category = categoryService.getOne(categoryQueryWrapper);
        String catId = category.getCatId();

        //根据cat_id去查找对应商品
        QueryWrapper<Goods> goodsQueryWrapper = new QueryWrapper<>();
        goodsQueryWrapper.eq("cat_id",catId);
        goodsQueryWrapper.orderByDesc("count_buy");
        List<Goods> goodsList = baseMapper.selectList(goodsQueryWrapper);

        return goodsList;
    }

    //添加一条商品
    @Override
    public boolean addGoods(String goodsInfo) {
        JSONObject jsonObject = JSONUtil.parseObj(goodsInfo);
        String title = jsonObject.getStr("title");
        String price = jsonObject.getStr("price");
        String catId = jsonObject.getStr("catId");
        String goodsImg = jsonObject.getStr("goodsImg");
        String goodsContent = jsonObject.getStr("goodsContent");

        Goods goods = new Goods();
        goods.setGoodsImg(goodsImg);
        goods.setTitle(title);
        goods.setPrice(Integer.valueOf(price));
        goods.setCatId(catId);
        goods.setGoodsContent(goodsContent);

        int res = baseMapper.insert(goods);
        if(res>0){
            return true;
        }else{
            return false;
        }
    }
}

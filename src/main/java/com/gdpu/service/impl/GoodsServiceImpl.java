package com.gdpu.service.impl;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gdpu.entity.Category;
import com.gdpu.entity.Goods;
import com.gdpu.mapper.GoodsMapper;
import com.gdpu.service.CategoryService;
import com.gdpu.service.GoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
    private RedisTemplate redisTemplate;

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
        String goodsId = jsonObject.getStr("goodsId");

        Goods tmpGoods = baseMapper.selectById(goodsId);

        Goods goods = new Goods();
        goods.setGoodsImg(goodsImg);
        goods.setTitle(title);
        goods.setPrice(Integer.valueOf(price));
        goods.setCatId(catId);
        goods.setGoodsContent(goodsContent);

        if (tmpGoods == null) {
            int res = baseMapper.insert(goods);
            if(res>0){
                return true;
            }else{
                return false;
            }
        }else{
            goods.setGoodsId(tmpGoods.getGoodsId());
            int res = baseMapper.updateById(goods);
            if(res>0){
                return true;
            }else{
                return false;
            }
        }

    }

    // 获取所有商品
    @Override
    public List<Goods> getAllGoods() {
        String goodsList = redisTemplate.opsForValue().get("goodsList",0,-1);


        //如果redis中没有缓存
        if (goodsList == null || goodsList.equals("")) {
            List<Goods> glist = baseMapper.selectList(null);
            JSONArray objects = JSONUtil.parseArray(glist);
            redisTemplate.opsForValue().set("goodsList",objects.toString());
            redisTemplate.expire("goodsList",1, TimeUnit.DAYS); //设置过期时间为1天
            return glist;
        }

        //有缓存就直接读取
        System.out.println("去redis中读取数据：。。。");
        String substring = goodsList.substring(1, goodsList.length()-1);
        String substring2 = StringEscapeUtils.unescapeJava(substring);
        JSONArray objects = JSONUtil.parseArray(substring2);
        List<Goods> res = JSONUtil.toList(objects, Goods.class);

        return res;

    }

}

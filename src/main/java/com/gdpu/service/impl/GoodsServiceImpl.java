package com.gdpu.service.impl;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gdpu.entity.BusinessGoods;
import com.gdpu.entity.Category;
import com.gdpu.entity.Goods;
import com.gdpu.entity.vo.GoodsSearchVo;
import com.gdpu.mapper.GoodsMapper;
import com.gdpu.service.BusinessGoodsService;
import com.gdpu.service.CategoryService;
import com.gdpu.service.GoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.*;
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
    BusinessGoodsService businessGoodsService;

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
        String businessId = jsonObject.getStr("businessId");

        Goods tmpGoods = baseMapper.selectById(goodsId);

        Goods goods = new Goods();
        goods.setGoodsImg(goodsImg);
        goods.setTitle(title);
        goods.setPrice(Integer.valueOf(price));
        goods.setCatId(catId);
        goods.setGoodsContent(goodsContent);



        //如果不存在这个商品就增加商品
        if (tmpGoods == null) {
            int res = baseMapper.insert(goods);
            boolean saveBg = true;
            if(!businessId.equals("")){
                //再向商家与商品的关联表 business_goods 中插入一条记录
                //先查询出刚才插入的这条goods的id
                QueryWrapper<Goods> goodsQueryWrapper = new QueryWrapper<>();
                goodsQueryWrapper.eq("title",goods.getTitle());
                goodsQueryWrapper.eq("goods_img",goods.getGoodsImg());
                goodsQueryWrapper.eq("price",goods.getPrice());
                goodsQueryWrapper.eq("cat_id",goods.getCatId());
                Goods selectone = baseMapper.selectOne(goodsQueryWrapper);
                String gid = selectone.getGoodsId();
                BusinessGoods businessGoods = new BusinessGoods();
                businessGoods.setBusinessId(businessId);
                businessGoods.setGoodsId(gid);
                saveBg = businessGoodsService.save(businessGoods);
            }


            return res > 0 && saveBg;
        }else{
            //否则就更新这个商品
            goods.setGoodsId(tmpGoods.getGoodsId());
            int res = baseMapper.updateById(goods);
            boolean saveBg = true;
            if(!businessId.equals("")) {
                //再向商家与商品的关联表 business_goods 中插入一条记录
                BusinessGoods businessGoods = new BusinessGoods();
                businessGoods.setBusinessId(businessId);
                businessGoods.setGoodsId(tmpGoods.getGoodsId());
                saveBg = businessGoodsService.save(businessGoods);
            }
            return res > 0 && saveBg;
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

    //保存用户商品浏览历史（Redis缓存）
    @Override
    public boolean saveGoodsHistory(String userId,String goodsId) {
        ZSetOperations<String,String> zSetOperations = redisTemplate.opsForZSet();

        //zset需要分数排序，拿当前时间当分数排序
        zSetOperations.add(userId,goodsId,System.currentTimeMillis());

        zSetOperations.removeRange(userId,0,-9);//保留8个商品

        return true;
    }

    //获取用户商品浏览历史（Redis缓存）
    @Override
    public List<Goods> getGoodsHistory(String userId) {
        //取得对应用户的商品ID

        if("".equals(userId)){
            return Collections.emptyList();
        }
        Set<String> goodIds = redisTemplate.opsForZSet().reverseRange(userId, 0, 7);
        List<Goods> goodsList = new ArrayList<>();

        if (goodIds != null) {
            goodIds.forEach(item->{
                //根据goodsId获取 商品的信息封装集合
                Goods goods = baseMapper.selectById(item);
                goodsList.add(goods);
            });
        }else{
            System.out.println("goodIds == null!!!!");
        }

        return goodsList;
    }

    //得到所有未分配商家的商品列表
    @Override
    public List<Goods> getGoodsNonDistribution() {
        List<Goods> goodsList = baseMapper.getGoodsNonDistribution();
        return goodsList;
    }

    //分页得到商品列表
    @Override
    public Map<String,Object> getPageGoods(long currentPage, long pageSize) {
        Page<Goods> goodsPage = new Page<>(currentPage,pageSize);
        baseMapper.selectPage(goodsPage,null);
        long total = goodsPage.getTotal();
        List<Goods> goodsList = goodsPage.getRecords();
        Map<String,Object> map = new HashMap<>();
        map.put("goodsList",goodsList);
        map.put("total",total);
        return map;
    }

    //分页查询得到商品列表
    @Override
    public Map<String, Object> getSearchGoods(long currentPage, long pageSize, String goodsSearchVo) {
        //获取参数
        JSONObject jsonObject = JSONUtil.parseObj(goodsSearchVo);
        JSONObject jsonObj2 = jsonObject.getJSONObject("goodsSearchVo");
        System.out.println("jsonObj2 = " + jsonObj2);
        GoodsSearchVo searchVo = JSONUtil.toBean(jsonObj2, GoodsSearchVo.class);
        System.out.println("searchVo = " + searchVo);

        String title = searchVo.getTitle();
        String business = searchVo.getBusiness();
        String sortType = searchVo.getSortType();
        Integer priceRange1 = searchVo.getPriceRange1();
        Integer priceRange2 = searchVo.getPriceRange2();

        System.out.println("title = " + title);
        System.out.println("business = " + business);
        System.out.println("priceRange1 = " + priceRange1);
        System.out.println("priceRange2 = " + priceRange2);

        Integer sort = null;
        if(sortType != null){
            if(sortType.equals("0")){
                sort = 0;
            }else{
                sort = 1;
            }
        }

        long begin = (currentPage - 1) * pageSize;
        List<Goods> goodsList = baseMapper.getSearchGoods(title, business, sort,
                priceRange1, priceRange2, begin, pageSize);

        int total = baseMapper.selectCount(null);

        Map<String, Object> resMap = new HashMap<>();
        resMap.put("goodsList",goodsList);
        resMap.put("total",total);

        return resMap;
    }


    //更新商品的浏览量
    @Override
    public boolean updateCountView(String goodsId) {

        //旧记录
        Goods oldGoods = baseMapper.selectById(goodsId);
        Integer oldView = oldGoods.getCountView();
        Integer newView = oldView+1;

        //新记录
        Goods newGoods = new Goods();
        newGoods.setGoodsId(goodsId);
        newGoods.setCountView(newView);
        int res = baseMapper.updateById(newGoods);
        return res>0;
    }

}

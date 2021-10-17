package com.gdpu.controller;


import cn.hutool.json.JSONObject;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gdpu.entity.Goods;
import com.gdpu.entity.Person;
import com.gdpu.entity.vo.GoodsSearchVo;
import com.gdpu.service.GoodsService;
import com.gdpu.service.PersonService;
import com.gdpu.utils.JwtUtil;
import com.gdpu.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author whd
 * @since 2021-10-08
 */
@RestController
@RequestMapping("/goods")
@CrossOrigin
public class GoodsController {

;

    @Autowired
    private GoodsService goodsService;

    //根据商品ID删除商品
    @PostMapping("/delGoods/{goodsId}")
    public R delGoods(@PathVariable String goodsId){
        boolean res = goodsService.removeById(goodsId);
        return res?R.ok():R.error();
    }

    //添加一个商品
    @PostMapping("/addGoods")
    public R addGoods(@RequestBody String goodsInfo){
        boolean flag = goodsService.addGoods(goodsInfo);
        return flag?R.ok():R.error();
    }

    //根据商品分类查询商品
    @GetMapping("/getGoodsByCatValue/{catValue}")
    public R getGoodsByID(@PathVariable String catValue){
        System.out.println("进入GoodsController - getGoodsByID");

        List<Goods> goodsList = goodsService.getGoodsByCatValue(catValue);

        return R.ok().data("goodsList",goodsList);
    }

    // 得到所有的商品
    // 采用redis缓存
    @GetMapping("/getAllGoods")
    public R getAllGoods(){
        List<Goods> goodsList = goodsService.getAllGoods();
        return R.ok().data("goodsList",goodsList);
    }

    //得到所有未分配商家的商品列表
    @GetMapping("/getGoodsNonDistribution")
    public R getGoodsNonDistribution(){
        List<Goods> goodsList = goodsService.getGoodsNonDistribution();
        return R.ok().data("goodsList",goodsList);
    }

    //分页得到商品列表
    @GetMapping("/getPageGoods/{currentPage}/{pageSize}")
    public R getPageGoods(@PathVariable long currentPage,@PathVariable long pageSize){
        Map<String, Object> map = goodsService.getPageGoods(currentPage, pageSize);
        Object goodsList = map.get("goodsList");
        Object total = map.get("total");
        return R.ok().data("goodsList",goodsList).data("total",total);
    }

    /**
     * 分页查询得到商品列表
     * @param currentPage 当前页
     * @param pageSize 页大小
     * @return
     */
    @PostMapping("/getSearchGoods/{currentPage}/{pageSize}")
    public R getSearchGoods(@PathVariable long currentPage, @PathVariable long pageSize,
                            @RequestBody String goodsSearchVo
                            ){
        System.out.println("goodsSearchVo = " + goodsSearchVo);
        //得到返回数据
        Map<String, Object> resMap = goodsService.getSearchGoods(currentPage,pageSize,goodsSearchVo);
        Object goodsList = resMap.get("goodsList");
        Object total = resMap.get("total");

        return R.ok().data("goodsList",goodsList).data("total",total);
    }


    /*根据ID获得一个商品的详情*/
    @GetMapping("/getGood/{goodsId}")
    public R getGood(@PathVariable String goodsId){
        System.out.println("进入GoodsController - /getGood/{id}");
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<Goods>();
        queryWrapper.eq("goods_id",goodsId);
        Goods goods = goodsService.getOne(queryWrapper);
        return R.ok().data("goods",goods);
    }

    //保存商品浏览历史（Redis缓存）
    @GetMapping("/saveGoodsHistory/{goodsId}")
    public R getRecentGoods(HttpServletRequest request,@PathVariable String goodsId){
        String token = request.getHeader("token");
        DecodedJWT verify = JwtUtil.verify(token);
        String userId = verify.getClaim("userId").asString();

        boolean res = goodsService.saveGoodsHistory(userId,goodsId);
        return R.ok();
    }

    //获取用户商品浏览历史记录（Redis缓存）
    @GetMapping("/getGoodsHistory")
    public R getRecentGoods(HttpServletRequest request){
        System.out.println("!!!GoodsController.getRecentGoods");

        String token = request.getHeader("token");
        DecodedJWT verify = JwtUtil.verify(token);
        String userId = verify.getClaim("userId").asString();

        List<Goods> goodsList = goodsService.getGoodsHistory(userId);
        return R.ok().data("goodsList",goodsList);
    }


}


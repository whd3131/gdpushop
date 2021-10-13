package com.gdpu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gdpu.entity.Goods;
import com.gdpu.entity.Person;
import com.gdpu.service.GoodsService;
import com.gdpu.service.PersonService;
import com.gdpu.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        System.out.println("进入GoodsController - getAllGoods方法");

        List<Goods> goodsList = goodsService.getAllGoods();


        //List<Goods> goodsList = goodsService.list();
        return R.ok().data("goodsList",goodsList);

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

}


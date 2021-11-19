package com.gdpu.controller;


import com.gdpu.entity.vo.CartVo;
import com.gdpu.service.CartService;
import com.gdpu.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/cart")
@CrossOrigin
public class CartController {
    @Autowired
    private CartService cartService;

    @PostMapping("/deleteById")
    public R deleteById(@RequestBody String id){
        boolean res = cartService.deleteById(id);
        return res?R.ok():R.error();
    }

    @PostMapping("/updateNums")
    public R updateNums(@RequestBody String updateNums){
        boolean res = cartService.updateNums(updateNums);
        return res?R.ok():R.error();
    }


    @PostMapping("/addCart")
    public R addCart(@RequestBody String cart){
        boolean save = cartService.saveCart(cart);
        if(save){
            return R.ok();
        }else{
            return R.error();
        }
    }

    @GetMapping("/getUserCart/{userId}")
    public R getUserCart(@PathVariable String userId){
        System.out.println("进入·CartController.getUserCart");
        List<CartVo> cartList = cartService.getUserCart(userId);
        return R.ok().data("cartList",cartList);
    }

}


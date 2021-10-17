package com.gdpu.controller;


import com.auth0.jwt.interfaces.DecodedJWT;
import com.gdpu.entity.UserAddress;
import com.gdpu.service.UserAddressService;
import com.gdpu.utils.JwtUtil;
import com.gdpu.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author whd
 * @since 2021-10-15
 */
@RestController
@RequestMapping("/user-address")
@CrossOrigin
public class UserAddressController {
    @Autowired
    private UserAddressService userAddressService;

    //删除地址
    @PostMapping("/delAddress/{uaId}")
    public R delAddress(@PathVariable String uaId){
        return userAddressService.removeById(uaId) ? R.ok() : R.error();
    }

    //编辑地址
    @PostMapping("/editAddress")
    public R editAddress(HttpServletRequest request,@RequestBody String addressInfo){
        String token = request.getHeader("token");
        DecodedJWT verify = JwtUtil.verify(token);
        String userId = verify.getClaim("userId").asString();

        boolean res = userAddressService.editAddress(userId,addressInfo);
        return res?R.ok():R.error();
    }

    //查询该用户所有的地址信息
    @GetMapping("/getAllAddress")
    public R getAllAddress(HttpServletRequest request){
        String token = request.getHeader("token");
        DecodedJWT verify = JwtUtil.verify(token);
        String userId = verify.getClaim("userId").asString();

        List<UserAddress> userAddressList = userAddressService.getAllAddressByUserId(userId);

        return R.ok().data("userAddressList",userAddressList);
    }


    //保存地址
    @PostMapping("/saveAddress")
    public R saveAddress(@RequestBody String addressInfo, HttpServletRequest request){
        String token = request.getHeader("token");
        DecodedJWT verify = JwtUtil.verify(token);
        String userId = verify.getClaim("userId").asString();

        boolean res = userAddressService.saveAddress(addressInfo,userId);

        return res?R.ok():R.error();

    }

}


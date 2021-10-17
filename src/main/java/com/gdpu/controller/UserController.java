package com.gdpu.controller;


import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.gdpu.entity.User;
import com.gdpu.entity.vo.LoginVo;
import com.gdpu.entity.vo.UserBuyVo;
import com.gdpu.service.UserService;
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
 * @since 2021-10-08
 */
@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    //获取用户的所有订单信息
    @GetMapping("/getUserOrder")
    public R getUserOrder(HttpServletRequest request){
        String token = request.getHeader("token");
        DecodedJWT verify = JwtUtil.verify(token);
        //token中带的用户信息
        String userId = verify.getClaim("userId").asString();
        String name = verify.getClaim("name").asString();

        List<UserBuyVo> userBuyVoList = userService.getUserOrder(userId);
        return R.ok().data("userBuyVoList",userBuyVoList);
    }

    //获取用户信息
    @GetMapping("/getUserInfo")
    public R getUserInfo(HttpServletRequest request){
        String token = request.getHeader("token");
        DecodedJWT verify = JwtUtil.verify(token);
        //token中带的用户信息
        String userId = verify.getClaim("userId").asString();
        String name = verify.getClaim("name").asString();

        return R.ok().data("userId",userId).data("name",name);
    }

    // 测试方法
    @GetMapping("/test/{token}")
    public R test(@PathVariable String token){
        System.out.println("***********\n当前token = " + token);
        try {
            DecodedJWT verify = JwtUtil.verify(token);
            //验证成功
            return R.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return R.error();
        }
    }


    //用户登录
    @PostMapping("/login")
    public R login(@RequestBody LoginVo loginVo){
        System.out.println("进入UserController -- login");

        String token = null;
        try {
            token = userService.login(loginVo);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error();
        }
        return R.ok().data("token",token);
    }

    // 增加用户
    @PostMapping("/addUser")
    public R addUser(@RequestBody String user){
        System.out.println("进入UserController -- addUser");

        JSONObject jsonObject = JSONUtil.parseObj(user);

        String name = jsonObject.getStr("name");
        String username = jsonObject.getStr("username");
        String password = jsonObject.getStr("password");
        String email = jsonObject.getStr("email");
        String avatar = jsonObject.getStr("avatar");
        String sex = jsonObject.getStr("sex");
        String phone = jsonObject.getStr("phone");

        User u = new User();
        u.setName(name);
        u.setUsername(username);
        u.setPassword(password);
        u.setEmail(email);
        u.setAvatar(avatar);
        u.setSex(Integer.parseInt(sex));
        u.setPhone(phone);


        boolean res = userService.save(u);

        if(res){
            return R.ok();
        }else{
            return R.error();
        }
    }

    //查询所有用户
    @GetMapping("getUserList")
    public R getUserList(){
        List<User> userList = userService.list();
        return R.ok().data("userList",userList);
    }
}


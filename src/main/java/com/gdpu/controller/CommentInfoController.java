package com.gdpu.controller;


import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gdpu.entity.CommentInfo;
import com.gdpu.entity.vo.CommentVo;
import com.gdpu.service.CommentInfoService;
import com.gdpu.utils.JwtUtil;
import com.gdpu.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 评论表 前端控制器
 * </p>
 *
 * @author whd
 * @since 2021-10-10
 */
@RestController
@RequestMapping("/comment")
@CrossOrigin
public class CommentInfoController {

    @Autowired
    CommentInfoService commentInfoService;

    //获得commentInfo表的所有内容
    @GetMapping("/getAllComment")
    public R getAllComment(){
        List<CommentInfo> commentInfoList = commentInfoService.list();
        return R.ok().data("commentInfoList",commentInfoList);
    }

    //分页查询该商品的评论
    @PostMapping("/getComment/{goodsId}/{currentPage}/{limit}")
    public R getComment(@PathVariable String goodsId,
                        @PathVariable long currentPage,
                        @PathVariable long limit,
                        HttpServletRequest request){

//        String token = request.getHeader("token");
//        DecodedJWT verify = JwtUtil.verify(token);
//        String userId = verify.getClaim("userId").asString();

        return commentInfoService.getCommentByPage(goodsId,null,currentPage,limit);
    }

    // 获得该商品所有评论
    @GetMapping("/getComment/{goodsId}")
    public R getComment(@PathVariable String goodsId, HttpServletRequest request){
        String token = request.getHeader("token");
        DecodedJWT verify = JwtUtil.verify(token);
        String userId = verify.getClaim("userId").asString();

        List<CommentVo> commentVoList = commentInfoService.getComment(goodsId,userId);
        return R.ok().data("commentVoList",commentVoList);
    }


    // 添加一条评论
    @PostMapping("/addComment")
    public R addComment(@RequestBody String goodsComment,HttpServletRequest request){
        //这里做验证，如果没有登录不给评论
        String token = request.getHeader("token");
        try {
            DecodedJWT verify = JwtUtil.verify(token);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error();
        }


        boolean res = commentInfoService.addComment(goodsComment);
        return res?R.ok():R.error();
    }
}


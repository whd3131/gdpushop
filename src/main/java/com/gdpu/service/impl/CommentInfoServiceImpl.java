package com.gdpu.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gdpu.entity.CommentInfo;
import com.gdpu.entity.Goods;
import com.gdpu.entity.User;
import com.gdpu.entity.vo.CommentVo;
import com.gdpu.mapper.CommentInfoMapper;
import com.gdpu.service.CommentInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdpu.service.GoodsService;
import com.gdpu.service.UserService;
import com.gdpu.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 评论表 服务实现类
 * </p>
 *
 * @author whd
 * @since 2021-10-10
 */
@Service
public class CommentInfoServiceImpl extends ServiceImpl<CommentInfoMapper, CommentInfo> implements CommentInfoService {

    @Autowired
    CommentInfoService commentInfoService;
    @Autowired
    UserService userService;
    @Autowired
    GoodsService goodsService;

    // 添加一条评论
    @Override
    public boolean addComment(String goodsComment) {
        JSONObject jsonObject = JSONUtil.parseObj(goodsComment);
        String goodsId = jsonObject.getStr("goodsId");
        String userId = jsonObject.getStr("userId");
        String content = jsonObject.getStr("content");
        String rate = jsonObject.getStr("rate");

        CommentInfo commentInfo = new CommentInfo();
        commentInfo.setGoodsId(goodsId);
        commentInfo.setUserId(userId);
        commentInfo.setContent(content);
        commentInfo.setRate(Integer.valueOf(rate));

        boolean flag1 = commentInfoService.save(commentInfo);

        //将该商品的评论数量增加1
        Goods goods = goodsService.getById(goodsId);
        Goods newGoods = new Goods();
        newGoods.setGoodsId(goodsId);
        newGoods.setCountComment(goods.getCountComment()+1);
        boolean flag2 = goodsService.updateById(newGoods);

        return flag1 && flag2;
    }

    // 获取所有评论
    @Override
    public List<CommentVo> getComment(String goodsId, String userId) {
        List<CommentVo> commentVoList = new ArrayList<>();
        //根据goodsId找到当前商品的评论【列表】
        QueryWrapper<CommentInfo> commentInfoQueryWrapper = new QueryWrapper<>();
        commentInfoQueryWrapper.eq("goods_id",goodsId);
        List<CommentInfo> commentInfoList = commentInfoService.list(commentInfoQueryWrapper);

        for (CommentInfo commentInfo : commentInfoList) {
            CommentVo commentVo = new CommentVo();
            commentVo.setContent(commentInfo.getContent());
            commentVo.setRate(commentInfo.getRate());

            Date createTime = commentInfo.getCreateTime();
            String createTimeFormat = DateUtil.formatDateTime(createTime);

            commentVo.setCreateTime(createTimeFormat);

            //根据USERID 得到用户头像昵称
            User user = userService.getById(commentInfo.getUserId());
            commentVo.setName(user.getName());
            commentVo.setAvatar(user.getAvatar());

            //封装成vo
            commentVoList.add(commentVo);
        }
        return commentVoList;
    }

    @Override
    public R getCommentByPage(String goodsId, String userId, long currentPage, long limit) {
        List<CommentVo> commentVoList = new ArrayList<>();
        //根据goodsId找到当前商品的评论【列表】
        QueryWrapper<CommentInfo> commentInfoQueryWrapper = new QueryWrapper<>();
        commentInfoQueryWrapper.eq("goods_id",goodsId);
        commentInfoQueryWrapper.orderByDesc("create_time");

        Page<CommentInfo> commentInfoPage = new Page<>(currentPage,limit);
        commentInfoService.page(commentInfoPage,commentInfoQueryWrapper);
        long total = commentInfoPage.getTotal();
        List<CommentInfo> commentInfoList = commentInfoPage.getRecords();
        for (CommentInfo commentInfo : commentInfoList) {
            CommentVo commentVo = new CommentVo();
            commentVo.setContent(commentInfo.getContent());
            commentVo.setRate(commentInfo.getRate());

            Date createTime = commentInfo.getCreateTime();
            String createTimeFormat = DateUtil.formatDateTime(createTime);

            commentVo.setCreateTime(createTimeFormat);

            //根据USERID 得到用户头像昵称
            User user = userService.getById(commentInfo.getUserId());
            commentVo.setName(user.getName());
            commentVo.setAvatar(user.getAvatar());

            //封装成vo
            commentVoList.add(commentVo);
        }

        return R.ok().data("total",total).data("commentVoList",commentVoList);
    }


}

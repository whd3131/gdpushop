package com.gdpu.service;

import com.gdpu.entity.CommentInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gdpu.entity.vo.CommentVo;
import com.gdpu.utils.R;

import java.util.List;

/**
 * <p>
 * 评论表 服务类
 * </p>
 *
 * @author whd
 * @since 2021-10-10
 */
public interface CommentInfoService extends IService<CommentInfo> {

    // 添加一条评论
    boolean addComment(String goodsComment);

    // 获取所有评论
    List<CommentVo> getComment(String goodsId, String userId);

    // 分页获取评论
    R getCommentByPage(String goodsId, String userId, long currentPage, long limit);
}

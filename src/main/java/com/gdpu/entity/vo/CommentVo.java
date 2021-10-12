package com.gdpu.entity.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author:王浩东
 * @createTime: 2021/10/10
 */
@Data
@ApiModel(value="CommentVo对象", description="用于展示前端评论")
public class CommentVo {
    @ApiModelProperty(value = "用户头像")
    private String avatar;

    @ApiModelProperty(value = "用户昵称")
    private String name;

    @ApiModelProperty(value = "评论内容")
    private String content;

    @ApiModelProperty(value = "评分评级")
    private Integer rate;

    @ApiModelProperty(value = "评论日期")
    private String createTime;
}

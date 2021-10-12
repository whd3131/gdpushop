package com.gdpu.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 评论表
 * </p>
 *
 * @author whd
 * @since 2021-10-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="CommentInfo对象", description="评论表")
public class CommentInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "评论ID")
    @TableId(value = "comment_id", type = IdType.ID_WORKER_STR)
    private String commentId;

    @ApiModelProperty(value = "关联用户ID")
    private String userId;

    @ApiModelProperty(value = "关联商品ID")
    private String goodsId;

    @ApiModelProperty(value = "评分评级")
    private Integer rate;

    @ApiModelProperty(value = "评论内容")
    private String content;

    @ApiModelProperty(value = "评论日期")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;


}

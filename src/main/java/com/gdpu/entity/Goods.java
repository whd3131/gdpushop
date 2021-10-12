package com.gdpu.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author whd
 * @since 2021-10-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Goods对象", description="")
public class Goods implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商品ID")
    @TableId(value = "goods_id", type = IdType.ID_WORKER_STR)
    private String goodsId;

    @ApiModelProperty(value = "商品名称")
    private String title;

    @ApiModelProperty(value = "商品描述")
    private String goodsContent;

    @ApiModelProperty(value = "商品价格")
    private Integer price;

    @ApiModelProperty(value = "商品图片")
    private String goodsImg;

    @ApiModelProperty(value = "商品分类ID")
    private String catId;

    @ApiModelProperty(value = "逻辑删除(0表示未删除，1表示删除）")
    @TableLogic
    private Integer isDeleted;

    @ApiModelProperty(value = "浏览量")
    private Integer countView;

    @ApiModelProperty(value = "购买量")
    private Integer countBuy;

    @ApiModelProperty(value = "评论数")
    private Integer countComment;


}

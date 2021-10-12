package com.gdpu.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 购物车的VO，主要展示商品的信息、用户的信息等
 * @author:王浩东
 * @createTime: 2021/10/8
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="CartVo对象", description="")
public class CartVo {
    @ApiModelProperty(value = "购物车ID号")
    private String id;

    @ApiModelProperty(value = "购买数量")
    private String nums;

    @ApiModelProperty(value = "商品名称")
    private String title;

    @ApiModelProperty(value = "商品价格")
    private Integer price;

    @ApiModelProperty(value = "商品图片")
    private String goodsImg;

    @ApiModelProperty(value = "商品分类ID")
    private String catId;

    @ApiModelProperty(value = "用户昵称")
    private String name;

    @ApiModelProperty(value = "电话")
    private String phone;
}

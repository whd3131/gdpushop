package com.gdpu.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author:王浩东
 * @createTime: 2021/10/9
 */
@Data
public class PayVo {

    @ApiModelProperty(value = "商品名称")
    private String title;

    @ApiModelProperty(value = "商品价格")
    private Integer price;

    @ApiModelProperty(value = "商品图片")
    private String goodsImg;

    @ApiModelProperty(value = "订单总价")
    private Integer orderPrice;

    @ApiModelProperty(value = "购买数量")
    private Integer nums;

    @ApiModelProperty(value = "用户昵称")
    private String name;

    @ApiModelProperty(value = "电话")
    private String phone;
}

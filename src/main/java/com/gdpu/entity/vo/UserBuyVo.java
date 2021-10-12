package com.gdpu.entity.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.gdpu.entity.Goods;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 * 用户个人订单记录
 * @author:王浩东
 * @createTime: 2021/10/10
 */

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="UserBuyVo对象", description="")
public class UserBuyVo {
    @ApiModelProperty(value = "订单号")
    private String orderId;

    @ApiModelProperty(value = "订单创建时间")
    private Date createTime;

    @ApiModelProperty(value = "订单总价")
    private Integer orderPrice;

    @ApiModelProperty(value = "交易状态(0:进行中1:已完成2:取消3:未付款)")
    private Integer orderStatus;

    @ApiModelProperty(value = "商品")
    private List<Goods> goodsList;

    @ApiModelProperty(value = "购买数量")
    private List<Integer> goodsNumsArray;


}

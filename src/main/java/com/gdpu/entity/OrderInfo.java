package com.gdpu.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;

import java.io.Serializable;
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
 * @since 2021-10-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="OrderInfo对象", description="")
public class OrderInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "订单号")
    @TableId(value = "order_id", type = IdType.ID_WORKER_STR)
    private String orderId;

    @ApiModelProperty(value = "订单创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty(value = "订单总价")
    private Integer orderPrice;

    @ApiModelProperty(value = "交易状态(0:进行中1:已完成2:取消)")
    private Integer orderStatus;

    @ApiModelProperty(value = "订单创建人")
    private String userId;

    @ApiModelProperty(value = "订单唯一标识")
    private String orderSign;

    @ApiModelProperty(value = "逻辑删除(0表示未删除，1表示删除）")
    @TableLogic
    private Integer isDeleted;


}

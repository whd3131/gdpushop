package com.gdpu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2021-10-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="UserAddress对象", description="")
public class UserAddress implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户地址ID")
      @TableId(value = "ua_id", type = IdType.ID_WORKER_STR)
    private String uaId;

    @ApiModelProperty(value = "关联用户ID")
    private String userId;

    @ApiModelProperty(value = "收货人")
    private String name;

    @ApiModelProperty(value = "收货地址")
    private String address;

    @ApiModelProperty(value = "电话")
    private String phone;


}

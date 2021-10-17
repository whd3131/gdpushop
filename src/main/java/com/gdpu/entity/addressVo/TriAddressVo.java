package com.gdpu.entity.addressVo;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 三级地址:区级城市
 * @author:王浩东
 * @createTime: 2021/10/14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="TriAddressVo对象", description="")
public class TriAddressVo {
    @TableId(value = "三级地址ID")
    private String addressId;

    @ApiModelProperty(value = "三级地址名称")
    private String addressTitle;
}

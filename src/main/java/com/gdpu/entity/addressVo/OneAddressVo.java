package com.gdpu.entity.addressVo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 一级地址:省级城市
 * @author:王浩东
 * @createTime: 2021/10/14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="OneAddressVo对象", description="")
public class OneAddressVo {

    @TableId(value = "一级地址ID")
    private String addressId;

    @ApiModelProperty(value = "一级地址名称")
    private String addressTitle;

    @ApiModelProperty(value = "二级地址集合")
    private List<TwoAddressVo> children;

}

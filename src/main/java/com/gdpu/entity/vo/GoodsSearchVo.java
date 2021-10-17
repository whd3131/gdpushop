package com.gdpu.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author:王浩东
 * @createTime: 2021/10/17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="GoodsSearchVo商品查询VO", description="")
public class GoodsSearchVo {
    @ApiModelProperty(value = "商品名称")
    private String title;

    @ApiModelProperty(value = "商家名称")
    private String business;

    @ApiModelProperty(value = "排序方式")
    private String sortType;

    @ApiModelProperty(value = "价格下限")
    private Integer priceRange1;

    @ApiModelProperty(value = "价格上限")
    private Integer priceRange2;
}

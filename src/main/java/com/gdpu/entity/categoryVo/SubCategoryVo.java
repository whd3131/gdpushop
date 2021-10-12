package com.gdpu.entity.categoryVo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author:王浩东
 * @createTime: 2021/10/10
 */
@Data
public class SubCategoryVo {
    @ApiModelProperty(value = "子分类ID值")
    private String cat_id;

    @ApiModelProperty(value = "子分类标题")
    private String label;

    @ApiModelProperty(value = "子分类值")
    private String value;

    @ApiModelProperty(value = "父分类ID值")
    private String parentId;
}

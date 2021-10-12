package com.gdpu.entity.categoryVo;

import com.gdpu.entity.categoryVo.SubCategoryVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author:王浩东
 * @createTime: 2021/10/10
 */
@Data
public class CategoryVo {
    @ApiModelProperty(value = "父分类ID值")
    private String cat_id;

    @ApiModelProperty(value = "父分类标题")
    private String label;

    @ApiModelProperty(value = "父分类值")
    private String value;

    @ApiModelProperty(value = "子分类集合")
    private List<SubCategoryVo> children;
}

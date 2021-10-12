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
 * @since 2021-10-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Category对象", description="")
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商品分类ID")
      @TableId(value = "cat_id", type = IdType.ID_WORKER_STR)
    private String catId;

    @ApiModelProperty(value = "商品分类标题")
    private String catName;

    @ApiModelProperty(value = "商品分类value")
    private String catValue;

    @ApiModelProperty(value = "父分类ID")
    private String parentId;


}

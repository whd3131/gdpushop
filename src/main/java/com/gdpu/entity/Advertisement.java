package com.gdpu.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Date;

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
 * @since 2021-10-11
 */

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Advertisement对象", description="")
public class Advertisement implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "广告ID")
    @TableId(value = "ad_id", type = IdType.ID_WORKER_STR)
    private String adId;

    @ApiModelProperty(value = "广告标语")
    private String adTitle;

    @ApiModelProperty(value = "关联的商品ID")
    private String goodsId;

    @ApiModelProperty(value = "广告的权重(数值越大权重越高)")
    private Integer adLevel;

    @ApiModelProperty(value = "广告创建日期")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;


}

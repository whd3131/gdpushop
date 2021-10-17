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
@ApiModel(value="Business对象", description="")
public class Business implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商家ID")
      @TableId(value = "business_id", type = IdType.ID_WORKER_STR)
    private String businessId;

    @ApiModelProperty(value = "商家名称")
    private String businessName;

    @ApiModelProperty(value = "好评数")
    private Integer countPraise;

    @ApiModelProperty(value = "商家简介")
    private String businessContent;


}

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
 * @since 2021-10-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="BaseAddress对象", description="")
public class BaseAddress implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "地址ID")
      @TableId(value = "address_id", type = IdType.ID_WORKER_STR)
    private String addressId;

    @ApiModelProperty(value = "地址名称")
    private String addressTitle;

    @ApiModelProperty(value = "父级地址ID")
    private String parentId;


}

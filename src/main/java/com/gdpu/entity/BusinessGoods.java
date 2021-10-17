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
@ApiModel(value="BusinessGoods对象", description="")
public class BusinessGoods implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商家-商品关联ID")
      @TableId(value = "bg_id", type = IdType.ID_WORKER_STR)
    private String bgId;

    @ApiModelProperty(value = "商家ID")
    private String businessId;

    @ApiModelProperty(value = "商品ID")
    private String goodsId;


}

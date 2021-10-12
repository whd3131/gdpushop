package com.gdpu.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * VO类，用于实现前端登录用户的功能
 * @author:王浩东
 * @createTime: 2021/10/8
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value="User对象-LoginVO", description="")
public class LoginVo {
    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;
}

package me.chenhz.api.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author CHZ
 * @create 2020/11/8
 */
@Data
public class LoginForm {

    @ApiModelProperty("用户名")
    @NotBlank(message = "用户名不能为空")
    private String UserName;

    @ApiModelProperty("密码")
    @NotBlank(message = "用户名不能为空")
    private String password;
}

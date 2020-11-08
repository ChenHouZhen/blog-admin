package me.chenhz.api.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@ApiModel(value = "用户表单")
public class UserForm {

    @ApiModelProperty(value = "用户名", required = true)
    @NotBlank(message ="用户名不能为空")
//    @Length(max = 50, message = "用户名称不能大于50个字符")
    private String username;

    @ApiModelProperty(value = "密码", required = true)
    @NotBlank(message = "密码不能为空")
    private String password;

    @ApiModelProperty(value = "邮箱", required = true)
//    @Length(max = 100, message = "邮箱长度不能大于100个字符")
    private String email;

    @ApiModelProperty(value = "手机号", required = true)
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "0?(13|14|15|17|18|19)[0-9]{9}", message = "这不是一个合法的手机号码")
//    @Length(max = 100, message = "手机号长度不能大于100个字符")
    private String mobile;

    private String avatar;

    private Integer deptId;

}

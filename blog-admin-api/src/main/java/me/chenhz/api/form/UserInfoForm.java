package me.chenhz.api.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("用户查询表单")
public class UserInfoForm extends PageForm {

    @ApiModelProperty(value = "状态",example = "1")
    private Integer status;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "部门ID",example = "1")
    private Integer deptId;

    @ApiModelProperty(value = "手机号")
    private String phone;

    /**
     * Springboot使用的默认json解析框架是jackjson框架
     *
     * jackjson解析框架在解析实体类里面是date数据类型的数据时的默认格式是：UTC类型
     *  即yyyy-MM-dd'T'HH:mm:ss.SSS 并且默认为+8时区，即时间基础上加8小时
     *
     *  参考：https://my.oschina.net/zicheng/blog/2963117
     */
    @ApiModelProperty(value = "开始时间")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;


    @ApiModelProperty(value = "结束时间")
    private Date endTime;
}

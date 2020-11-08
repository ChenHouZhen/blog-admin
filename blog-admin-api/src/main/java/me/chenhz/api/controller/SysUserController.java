package me.chenhz.api.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import me.chenhz.api.config.JwtUtils;
import me.chenhz.api.dto.R;
import me.chenhz.api.entity.SysUserEntity;
import me.chenhz.api.form.UserForm;
import me.chenhz.api.form.UserInfoForm;
import me.chenhz.api.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * <p>
 * 系统用户 前端控制器
 * </p>
 *
 * @author chenhz
 * @since 2020-11-02
 */
@Slf4j
@RestController
@RequestMapping("/sys/v1/users")
@Api(tags = "用户管理")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;


    @PostMapping()
    @ApiOperation("新增")
    public R add(@RequestBody UserForm form){
        SysUserEntity entity = new SysUserEntity();
        entity.setUsername(form.getUsername());
        entity.setCreateTime(new Date());
        entity.setEmail(form.getEmail());
        entity.setMobile(form.getMobile());
        entity.setPassword(form.getPassword());
        entity.setDeptId(form.getDeptId());
        entity.setAvatar(form.getAvatar());
        entity.setStatus(1);

        sysUserService.save(entity);

        return R.ok().put("data",sysUserService.getById(entity.getUserId()));
    }

    @PutMapping(value = "/{userId}")
    @ApiOperation("修改")
    public R edit(@PathVariable(value = "userId") Long userId,@RequestBody UserForm form){
        SysUserEntity entity = new SysUserEntity();
        entity.setUserId(userId);
        entity.setUsername(form.getUsername());
        entity.setEmail(form.getEmail());
        entity.setMobile(form.getMobile());
        entity.setPassword(form.getPassword());
        entity.setStatus(1);

        sysUserService.updateById(entity);
        return R.ok().put("data",sysUserService.getById(userId));
    }

    @GetMapping(value = "/{userId}")
    @ApiOperation("根据uid查询")
    public R info(@PathVariable(value = "userId") Long userId){
        return R.ok().put("data",sysUserService.getById(userId));
    }

    @GetMapping(value = "/info")
    @ApiOperation("根据token查询")
    public R info(@RequestParam(value = "token")  String token){
        SysUserEntity user = sysUserService.queryByUsername(JwtUtils.getUsername(token));
        return R.ok().put("data",user);
    }

    @GetMapping()
    @ApiOperation("查询")
    public R infos(UserInfoForm form){
        Page data = sysUserService.page(form);
        return R.ok().put("data",data);
    }

    @DeleteMapping("/{userId}")
    @ApiOperation("删除")
    public R delete(@PathVariable Long userId){
        SysUserEntity entity = new SysUserEntity();
        entity.setUserId(userId);
        entity.setStatus(0);
        sysUserService.updateById(entity);

        return R.ok().put("data",sysUserService.getById(userId));
    }


}

package me.chenhz.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.chenhz.api.dto.R;
import me.chenhz.api.entity.SysUserEntity;
import me.chenhz.api.form.LoginForm;
import me.chenhz.api.service.SysUserService;
import me.chenhz.api.shiro.ShiroUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * @author CHZ
 * @create 2019/5/1
 */
@RestController
@Api(tags = "登录中心")
public class SysLoginController {


    @Autowired
    private SysUserService sysUserService;

    /**
     * 登录
     */
    @RequestMapping(value = "/sys/login", method = RequestMethod.POST)
    @ApiOperation(value = "登录")
    public R login(@RequestBody LoginForm form) {
//        String kaptcha = ShiroUtils.getKaptcha(Constants.KAPTCHA_SESSION_KEY);
//        if(!captcha.equalsIgnoreCase(kaptcha)){
//            return R.error("验证码不正确");
//        }

        try{
            Subject subject = ShiroUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(form.getUserName(), form.getPassword());
            subject.login(token);

            SysUserEntity user = (SysUserEntity) subject.getPrincipal();
            String newToken = sysUserService.generateJwtToken(user.getUsername());
//            response.setHeader("x-auth-token", newToken);

            Map<String, Object> map = new HashMap<>();
            map.put("token", newToken);

            return R.ok().put("data",map);
        }catch (UnknownAccountException e) {
            return R.error(e.getMessage());
        }catch (IncorrectCredentialsException e) {
            return R.error("账号或密码不正确");
        }catch (LockedAccountException e) {
            return R.error("账号已被锁定,请联系管理员");
        }catch (AuthenticationException e) {
            return R.error("账户验证失败");
        }

    }

    /**
     * 退出
     */
    @ApiOperation(value = "退出")
    @RequestMapping(value = "/sys/logout", method = RequestMethod.POST)
    public R logout() {
        ShiroUtils.logout();
//        return "redirect:login.html";
        return R.ok("退出成功");
    }

}

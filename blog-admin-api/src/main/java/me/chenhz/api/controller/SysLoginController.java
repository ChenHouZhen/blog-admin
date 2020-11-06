package me.chenhz.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.chenhz.api.dto.R;
import me.chenhz.api.shiro.ShiroUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author CHZ
 * @create 2019/5/1
 */
@RestController
@Api(tags = "登录中心")
public class SysLoginController {


    /**
     * 登录
     */
    @RequestMapping(value = "/sys/login", method = RequestMethod.POST)
    @ApiOperation(value = "登录")
    public R login(String username, String password, String captcha) {
//        String kaptcha = ShiroUtils.getKaptcha(Constants.KAPTCHA_SESSION_KEY);
//        if(!captcha.equalsIgnoreCase(kaptcha)){
//            return R.error("验证码不正确");
//        }

        try{
            Subject subject = ShiroUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            subject.login(token);
        }catch (UnknownAccountException e) {
            return R.error(e.getMessage());
        }catch (IncorrectCredentialsException e) {
            return R.error("账号或密码不正确");
        }catch (LockedAccountException e) {
            return R.error("账号已被锁定,请联系管理员");
        }catch (AuthenticationException e) {
            return R.error("账户验证失败");
        }

        return R.ok();
    }

    /**
     * 退出
     */
    @ApiOperation(value = "退出")
    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public String logout() {
        ShiroUtils.logout();
        return "redirect:login.html";
    }
}

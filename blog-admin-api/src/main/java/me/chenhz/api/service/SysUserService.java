package me.chenhz.api.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.chenhz.api.entity.SysUserEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import me.chenhz.api.form.UserInfoForm;

/**
 * <p>
 * 系统用户 服务类
 * </p>
 *
 * @author chenhz
 * @since 2020-11-02
 */
public interface SysUserService extends IService<SysUserEntity> {

    SysUserEntity queryByUsername(String userName);

    String generateJwtToken(String userName);

    Page page(UserInfoForm form);
}

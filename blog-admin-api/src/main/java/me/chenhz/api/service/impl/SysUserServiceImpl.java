package me.chenhz.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.chenhz.api.config.JwtUtils;
import me.chenhz.api.entity.SysUserEntity;
import me.chenhz.api.form.UserInfoForm;
import me.chenhz.api.mapper.SysUserMapper;
import me.chenhz.api.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.chenhz.api.utils.PageUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 系统用户 服务实现类
 * </p>
 *
 * @author chenhz
 * @since 2020-11-02
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUserEntity> implements SysUserService {

    @Override
    @Transactional  // 开启事务
    public SysUserEntity queryByUsername(String userName) {
        return this.getOne(
                new LambdaQueryWrapper<SysUserEntity>().eq(SysUserEntity::getUsername,userName)
        );

    }

    @Override
    public String generateJwtToken(String userName) {

        SysUserEntity user = this.queryByUsername(userName);

        return JwtUtils.sign(user.getUsername(), user.getSalt(), 3600); //生成jwt token，设置过期时间为1小时
    }


    @Override
    public Page page(UserInfoForm form) {
        Page page = new Page(PageUtils.getPage(form), PageUtils.getLimit(form));

        this.page(page, new LambdaQueryWrapper<SysUserEntity>()
                .eq(!StringUtils.isEmpty(form.getStatus()),SysUserEntity::getStatus,form.getStatus())
                .eq(!StringUtils.isEmpty(form.getDeptId()),SysUserEntity::getDeptId,form.getDeptId())
                .like(!StringUtils.isEmpty(form.getUserName()),SysUserEntity::getUsername,form.getUserName())
                .like(!StringUtils.isEmpty(form.getPhone()),SysUserEntity::getMobile,form.getPhone())
                .gt(!StringUtils.isEmpty(form.getStartTime()),SysUserEntity::getCreateTime,form.getStartTime())
                .lt(!StringUtils.isEmpty(form.getEndTime()),SysUserEntity::getCreateTime,form.getEndTime())
                .orderByDesc(SysUserEntity::getCreateTime)
        );

        return page;
    }

}

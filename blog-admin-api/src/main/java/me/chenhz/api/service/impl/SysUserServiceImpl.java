package me.chenhz.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import me.chenhz.api.entity.SysUserEntity;
import me.chenhz.api.mapper.SysUserMapper;
import me.chenhz.api.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

}

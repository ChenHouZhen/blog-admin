package me.chenhz.api.service.impl;

import me.chenhz.api.entity.SysUserEntity;
import me.chenhz.api.mapper.SysUserMapper;
import me.chenhz.api.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}

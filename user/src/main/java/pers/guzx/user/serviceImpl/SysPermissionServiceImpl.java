package pers.guzx.user.serviceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pers.guzx.common.mapper.BaseMapper;
import pers.guzx.common.serviceImpl.BaseServiceImpl;
import pers.guzx.user.entity.SysPermission;
import pers.guzx.user.mapper.SysPermissionMapper;
import pers.guzx.user.service.SysPermissionService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/7/8 11:22
 * @describe
 */
@Slf4j
@Service
public class SysPermissionServiceImpl extends BaseServiceImpl<SysPermission> implements SysPermissionService {

    @Resource
    private SysPermissionMapper sysPermissionMapper;

    @Override
    public BaseMapper<SysPermission> getMapper() {
        return this.sysPermissionMapper;
    }

    public List<SysPermission> allPermission() {
        List<SysPermission> sysPermissions = sysPermissionMapper.selectAll();
        return sysPermissions;
    }

}

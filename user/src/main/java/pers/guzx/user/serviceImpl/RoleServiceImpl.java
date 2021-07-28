package pers.guzx.user.serviceImpl;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import pers.guzx.common.code.ErrorCode;
import pers.guzx.common.exception.BaseException;
import pers.guzx.user.entity.SysRole;
import pers.guzx.user.entity.SysUserDetails;
import pers.guzx.user.entity.UserRole;
import pers.guzx.user.mapper.RoleMapper;
import pers.guzx.user.mapper.UserRoleMapper;
import pers.guzx.user.service.RoleService;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/7/19 11:36
 * @describe
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleMapper roleMapper;
    @Resource
    private UserRoleMapper userRoleMapper;

    @Override
    public UserRole getUserRole(Integer userId) {
        return null;
    }

    @Override
    public GrantedAuthority getRoleById(UserRole userRole) {
        SysRole role = roleMapper.selectByPrimaryKey(userRole.getRoleId());
        return role;
    }

    @Override
    public UserRole getUserRoleByUser(UserDetails user) {
        Example example = new Example(UserRole.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", ((SysUserDetails) user).getUserId());
        List<UserRole> userRoles = userRoleMapper.selectByExample(example);
        if (userRoles.size() > 0) {
            return userRoles.get(0);
        }
        return null;
    }

    @Override
    public int saveUserRole(UserDetails userDetails) {
        Example example = new Example(SysRole.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("roleCode", ((SysUserDetails)userDetails).getRole());
        List<SysRole> sysRoles = roleMapper.selectByExample(example);
        if (sysRoles.size() < 1) {
            throw new BaseException(ErrorCode.USER_ROLE_ERROR);
        }
        SysRole role = sysRoles.get(0);
        UserRole userRole = new UserRole();
        userRole.setRoleId(role.getRoleId());
        userRole.setUserId(((SysUserDetails)userDetails).getUserId());
        return userRoleMapper.insert(userRole);
    }

    @Override
    public SysRole findById(int id) {
        return null;
    }

    @Override
    public int save(SysRole role) {
        return roleMapper.insert(role);
    }

    @Override
    public int update(SysRole sysRole) {
        return 0;
    }

    @Override
    public int remove(int id) {
        return 0;
    }

    public UserRole save(UserRole userRole) {
        int insert = userRoleMapper.insert(userRole);
        return userRole;
    }
}

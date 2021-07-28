package pers.guzx.user.serviceImpl;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import pers.guzx.user.entity.*;
import pers.guzx.user.mapper.AuthorityMapper;
import pers.guzx.user.mapper.RoleAuthorityMapper;
import pers.guzx.user.mapper.UserAuthorityMapper;
import pers.guzx.user.service.AuthorityService;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/7/19 11:37
 * @describe
 */
@Service
public class AuthorityServiceImpl implements AuthorityService {

    @Resource
    private AuthorityMapper authorityMapper;
    @Resource
    private RoleAuthorityMapper roleAuthorityMapper;
    @Resource
    private UserAuthorityMapper userAuthorityMapper;

    @Override
    public UserAuthority getUserAuthority(Integer userId) {
        return null;
    }

    @Override
    public List<GrantedAuthority> getAuthorityById(List<UserAuthority> userAuthorities) {

        List<GrantedAuthority> authorities = new ArrayList<>();
        userAuthorities.stream().map(userAuthority -> {
            Example example = new Example(SysAuthority.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("authorityId", userAuthority.getAuthorityId());
            SysAuthority authority = authorityMapper.selectOneByExample(example);
            return authority;
        }).forEach(authorities::add);
        return authorities;
    }

    @Override
    public List<UserAuthority> getUserAuthorityByUser(UserDetails user) {
        Example example = new Example(UserAuthority.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", ((SysUserDetails) user).getUserId());
        List<UserAuthority> userAuthorities = userAuthorityMapper.selectByExample(example);
        return userAuthorities;
    }

    public List<GrantedAuthority> getAuthorityByRole(SysRole role) {

        List<GrantedAuthority> authorities = new ArrayList<>();
        Example example = new Example(RoleAuthority.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("roleId", role.getRoleId());

        // 从角色权限中间表中获取权限信息
        List<RoleAuthority> roleAuthorities = roleAuthorityMapper.selectByExample(example);

        roleAuthorities.stream().map(roleAuthority ->
                authorityMapper.selectByPrimaryKey(roleAuthority.getAuthorityId()))
                .forEach(authorities::add);

        return authorities;
    }

    @Override
    public SysAuthority findById(int id) {
        return null;
    }

    @Override
    public int save(SysAuthority authority) {
        return authorityMapper.insert(authority);
    }

    @Override
    public int update(SysAuthority sysAuthority) {
        return 0;
    }

    @Override
    public int remove(int id) {
        return 0;
    }

    public RoleAuthority save(RoleAuthority roleAuthority) {
        int insert = roleAuthorityMapper.insert(roleAuthority);
        return roleAuthority;
    }

    public UserAuthority save(UserAuthority userAuthority) {
        int insert = userAuthorityMapper.insert(userAuthority);
        return userAuthority;
    }
}

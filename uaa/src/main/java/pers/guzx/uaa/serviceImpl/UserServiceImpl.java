package pers.guzx.uaa.serviceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.guzx.uaa.entity.SysUserDetails;
import pers.guzx.uaa.mapper.UserMapper;
import pers.guzx.uaa.service.RoleService;
import pers.guzx.uaa.service.UserService;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/7/6 15:05
 * @describe
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private RoleService roleService;
    @Resource
    private PasswordEncoder passwordEncoder;

    public UserDetails findByPhone(String phone) {
        Example example = new Example(SysUserDetails.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("phone", phone);
        List<SysUserDetails> sysUserDetails = userMapper.selectByExample(example);
        if (sysUserDetails.size() > 0) {
            return sysUserDetails.get(0);
        }
        return null;
    }

    public UserDetails findByEmail(String email) {
        Example example = new Example(SysUserDetails.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("email", email);
        List<SysUserDetails> sysUserDetails = userMapper.selectByExample(example);
        if (sysUserDetails.size() > 0) {
            return sysUserDetails.get(0);
        }
        return null;
    }

    @Override
    public UserDetails getUserByUsername(String username) {
        Example example = new Example(SysUserDetails.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username", username);
        List<SysUserDetails> sysUserDetails = userMapper.selectByExample(example);
        if (sysUserDetails.size() > 0) {
            return sysUserDetails.get(0);
        }
        return null;
    }

    @Override
    public SysUserDetails findById(int id) {
        return null;
    }

    @Override
    public int save(SysUserDetails sysUserDetails) {
        String encode = passwordEncoder.encode(sysUserDetails.getPassword());
        sysUserDetails.setPassword(encode);
        sysUserDetails.setEnabled(true);
        sysUserDetails.setAccountNonLocked(true);
        sysUserDetails.setAccountNonExpired(true);
        sysUserDetails.setCredentialsNonExpired(true);
        userMapper.insert(sysUserDetails);
        return roleService.saveUserRole(sysUserDetails);
    }

    @Override
    public int update(SysUserDetails sysUserDetails) {
        return 0;
    }

    @Override
    public int remove(int id) {
        return 0;
    }
}

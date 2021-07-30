package pers.guzx.user.serviceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.guzx.common.code.ErrorCode;
import pers.guzx.common.dto.JsonDto;
import pers.guzx.common.exception.BaseException;
import pers.guzx.common.mapper.BaseMapper;
import pers.guzx.common.serviceImpl.BaseServiceImpl;
import pers.guzx.user.client.NoticeClient;
import pers.guzx.user.client.UaaClient;
import pers.guzx.user.entity.JWT;
import pers.guzx.user.entity.RoleAuthority;
import pers.guzx.user.entity.SysUserDetails;
import pers.guzx.user.mapper.RoleAuthorityMapper;
import pers.guzx.user.mapper.UserAuthorityMapper;
import pers.guzx.user.mapper.UserMapper;
import pers.guzx.user.service.RoleService;
import pers.guzx.user.service.UserService;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
    private RoleAuthorityMapper roleAuthorityMapper;
    @Resource
    private UserAuthorityMapper userAuthorityMapper;
    @Resource
    private RoleService roleService;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private NoticeClient noticeClient;

    public SysUserDetails findByPhone(String phone) {
        Example example = new Example(SysUserDetails.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("phone", phone);
        List<SysUserDetails> sysUserDetails = userMapper.selectByExample(example);
        if (sysUserDetails.size() > 0) {
            return sysUserDetails.get(0);
        }
        return null;
    }

    public SysUserDetails findByEmail(String email) {
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
    @Async
    public void sendRegistryCode(String email) {
        SysUserDetails byEmail = findByEmail(email);
        if (Objects.nonNull(byEmail)) {
            throw new BaseException(ErrorCode.USER_INFO_EXIST);
        }


        noticeClient.sendVerificationCode(email);
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

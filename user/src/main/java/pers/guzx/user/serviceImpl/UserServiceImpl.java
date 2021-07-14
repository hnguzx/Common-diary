package pers.guzx.user.serviceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pers.guzx.common.mapper.BaseMapper;
import pers.guzx.common.serviceImpl.BaseServiceImpl;
import pers.guzx.user.entity.SysUser;
import pers.guzx.user.mapper.UserMapper;
import pers.guzx.user.service.UserService;
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
public class UserServiceImpl extends BaseServiceImpl<SysUser> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public BaseMapper<SysUser> getMapper() {
        return this.userMapper;
    }

    public SysUser findByPhone(String phone) {
        Example example = new Example(SysUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("phone", phone);
        List<SysUser> sysUsers = userMapper.selectByExample(example);
        if (sysUsers.size() > 0) {
            return sysUsers.get(0);
        }
        return null;
    }

    public SysUser findByUserName(String username) {
        Example example = new Example(SysUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username", username);
        List<SysUser> sysUsers = userMapper.selectByExample(example);
        if (sysUsers.size() > 0) {
            return sysUsers.get(0);
        }
        return null;
    }

    public SysUser findByEmail(String email) {
        Example example = new Example(SysUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("email", email);
        List<SysUser> sysUsers = userMapper.selectByExample(example);
        if (sysUsers.size() > 0) {
            return sysUsers.get(0);
        }
        return null;
    }
}

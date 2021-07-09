package pers.guzx.user.serviceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import pers.guzx.common.mapper.BaseMapper;
import pers.guzx.common.serviceImpl.BaseServiceImpl;
import pers.guzx.common.util.MobileUtil;
import pers.guzx.user.entity.SysRole;
import pers.guzx.user.entity.User;
import pers.guzx.user.mapper.UserMapper;
import pers.guzx.user.service.UserService;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/7/6 15:05
 * @describe
 */
@Slf4j
@Service
public class UserServiceImpl extends BaseServiceImpl<User> {

    @Resource
    private UserMapper userMapper;

    @Override
    public BaseMapper<User> getMapper() {
        return this.userMapper;
    }


    public User findByPhone(String phone) {
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("phone", phone);
        List<User> users = userMapper.selectByExample(example);
        if (users.size() > 0) {
            return users.get(0);
        }
        return null;
    }

    public User findByUserName(String username){
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username", username);
        List<User> users = userMapper.selectByExample(example);
        if (users.size() > 1) {
            return users.get(0);
        }
        return null;
    }
}

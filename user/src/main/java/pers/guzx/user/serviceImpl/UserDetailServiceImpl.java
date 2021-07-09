package pers.guzx.user.serviceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StringUtils;
import pers.guzx.common.util.MobileUtil;
import pers.guzx.user.entity.User;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/7/9 11:13
 * @describe
 */
@Slf4j
@Configuration
public class UserDetailServiceImpl implements UserDetailsService {

    @Resource
    private UserServiceImpl userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if (StringUtils.isEmpty(username)) {
            log.info("UserDetailsService没有接收到用户账号");
            throw new UsernameNotFoundException("UserDetailsService没有接收到用户账号");
        } else {
            //根据用户名查找用户信息
            User user = null;
            if (MobileUtil.isMobileNO(username)) {
                //手机号验证码登陆
                user = userService.findByPhone(username);
            } else {
                //用户名, 密码登陆
                user = userService.findByUserName(username);
            }
            if (user == null) {
                throw new UsernameNotFoundException(String.format("用户不存在", username));
            }
            //新建权限集合
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            //模拟从数据库获取角色权限
//            List<SysRole> sysRoleDTOList = user.getRoleList();
//            for (SysRole role : sysRoleDTOList) {
//                //封装用户信息和角色信息到SecurityContextHolder全局缓存中
//                grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
//            }
            return user;
        }
    }
}

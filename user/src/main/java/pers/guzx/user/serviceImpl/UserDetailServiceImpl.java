package pers.guzx.user.serviceImpl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import pers.guzx.common.util.EmailUtil;
import pers.guzx.common.util.MobileUtil;
import pers.guzx.user.entity.SysUser;

import javax.annotation.Resource;

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

    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if (StringUtils.isEmpty(username)) {
            log.info("UserDetailsService没有接收到用户账号");
            throw new UsernameNotFoundException("用户不存在");
        } else {
            SysUser sysUser = null;
            if (MobileUtil.isMobile(username)) {
                sysUser = userService.findByPhone(username);
            } else if (EmailUtil.isEmail(username)) {
                sysUser = userService.findByEmail(username);
            } else {
                sysUser = userService.findByUserName(username);
            }
            if (sysUser == null) {
                throw new UsernameNotFoundException(String.format("用户不存在", username));
            }
            return new User(sysUser.getUsername(), passwordEncoder.encode(sysUser.getPassword()),
                    AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
        }
    }
}

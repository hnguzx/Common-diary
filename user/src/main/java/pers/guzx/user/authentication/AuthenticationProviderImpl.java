package pers.guzx.user.authentication;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import pers.guzx.user.entity.SysUserDetails;
import pers.guzx.user.exception.BadVerificationCodeException;

import javax.annotation.Resource;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/7/8 16:29
 * @describe 自定义认证校验器。参考：AbstractUserDetailsAuthenticationProvider
 */

@Data
@Slf4j
@Configuration
public class AuthenticationProviderImpl implements AuthenticationProvider {

    private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();
    private UserDetailsChecker preAuthenticationChecks = new AuthenticationProviderImpl.DefaultPreAuthenticationChecks();
    private UserDetailsChecker postAuthenticationChecks = new AuthenticationProviderImpl.DefaultPostAuthenticationChecks();
    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private UserDetailsService authDetailsService;
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = determineUsername(authentication);
        UserDetails user = null;
        user = JSONObject.parseObject((String) redisTemplate.opsForHash().get("user", username), SysUserDetails.class);
        if (user == null) {
            try {
                user = retrieveUser(username, (AuthenticationToken) authentication);
            } catch (UsernameNotFoundException ex) {
                log.debug("Failed to find user '" + username + "'");
                throw ex;
            }
        }

        try {
            this.preAuthenticationChecks.check(user);
            additionalAuthenticationChecks(user, (AuthenticationToken) authentication);
        } catch (AuthenticationException ex) {
            user = retrieveUser(username, (AuthenticationToken) authentication);
            this.preAuthenticationChecks.check(user);
            additionalAuthenticationChecks(user, (AuthenticationToken) authentication);
        }
        this.postAuthenticationChecks.check(user);
        // 认证成功后将principal设置为用户信息
        Object principalToReturn = user;

        return createSuccessAuthentication(principalToReturn, authentication, user);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return AuthenticationToken.class.isAssignableFrom(authentication);
    }

    public void setAuthoritiesMapper(GrantedAuthoritiesMapper authoritiesMapper) {
        this.authoritiesMapper = authoritiesMapper;
    }

    private String determineUsername(Authentication authentication) {
        return (authentication.getPrincipal() == null) ? "NONE_PROVIDED" : authentication.getName();
    }

    /**
     * 获取用户信息
     *
     * @param username
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    protected UserDetails retrieveUser(String username, AuthenticationToken authentication)
            throws AuthenticationException {
        try {
            UserDetails loadedUser = this.authDetailsService.loadUserByUsername(username);
            if (loadedUser == null) {
                throw new InternalAuthenticationServiceException(
                        "UserDetailsService returned null, which is an interface contract violation");
            }
            return loadedUser;
        } catch (UsernameNotFoundException ex) {
            throw ex;
        } catch (InternalAuthenticationServiceException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex);
        }
    }

    /**
     * 校验账户的凭证
     *
     * @param userDetails
     * @param authentication
     * @throws AuthenticationException
     */
    protected void additionalAuthenticationChecks(UserDetails userDetails,
                                                  AuthenticationToken authentication) throws AuthenticationException {

        if (authentication.getCredentials() == null) {
            log.debug("Failed to authenticate since no credentials provided");
            throw new BadCredentialsException(this.messages
                    .getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
        }

        Boolean mobileCode = authentication.getMobileCode();
        String presentedPassword = authentication.getCredentials().toString();
        if (mobileCode) {
            String mobilePhone = authentication.getPrincipal().toString();
            // 校验手机验证码
            String verificationCode = redisTemplate.opsForValue().get("login:" + mobilePhone);
            if (!presentedPassword.equals(verificationCode)) {
                log.debug("Failed to authenticate since password does not match stored value");
                throw new BadVerificationCodeException(this.messages
                        .getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
            }
        } else {
            // 校验密码
            if (!this.passwordEncoder.matches(presentedPassword, userDetails.getPassword())) {
                log.debug("Failed to authenticate since password does not match stored value");
                throw new BadCredentialsException(this.messages
                        .getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
            }
        }


    }

    /**
     * 认证完成
     *
     * @param principal
     * @param authentication
     * @param user
     * @return
     */
    protected Authentication createSuccessAuthentication(Object principal, Authentication authentication,
                                                         UserDetails user) {
        AuthenticationToken result = new AuthenticationToken(principal,
                authentication.getCredentials(), this.authoritiesMapper.mapAuthorities(user.getAuthorities()));
        result.setDetails(authentication.getDetails());
        // 缓存用户信息5分钟
//        redisTemplate.opsForHash()
//                .putIfAbsent("user", user.getUsername(), JSONObject.toJSONString(user));
//        redisTemplate.expire("user", 300, TimeUnit.SECONDS);
        log.debug("Authenticated user");
        return result;
    }

    /**
     * 账户的可用性校验
     */
    private class DefaultPreAuthenticationChecks implements UserDetailsChecker {

        @Override
        public void check(UserDetails user) {
            if (!user.isAccountNonLocked()) {
                log.debug("Failed to authenticate since user account is locked");
                throw new LockedException(AuthenticationProviderImpl.this.messages
                        .getMessage("AbstractUserDetailsAuthenticationProvider.locked", "User account is locked"));
            }
            if (!user.isEnabled()) {
                log.debug("Failed to authenticate since user account is disabled");
                throw new DisabledException(AuthenticationProviderImpl.this.messages
                        .getMessage("AbstractUserDetailsAuthenticationProvider.disabled", "User is disabled"));
            }
            if (!user.isAccountNonExpired()) {
                log.debug("Failed to authenticate since user account has expired");
                throw new AccountExpiredException(AuthenticationProviderImpl.this.messages
                        .getMessage("AbstractUserDetailsAuthenticationProvider.expired", "User account has expired"));
            }
        }

    }

    /**
     * 账户密码的可用性校验
     */
    private class DefaultPostAuthenticationChecks implements UserDetailsChecker {

        @Override
        public void check(UserDetails user) {
            if (!user.isCredentialsNonExpired()) {
                log.debug("Failed to authenticate since user account credentials have expired");
                throw new CredentialsExpiredException(AuthenticationProviderImpl.this.messages
                        .getMessage("AbstractUserDetailsAuthenticationProvider.credentialsExpired",
                                "User credentials have expired"));
            }
        }

    }
}

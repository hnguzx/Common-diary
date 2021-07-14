package pers.guzx.user.authen;

import lombok.Data;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import pers.guzx.common.code.ErrorCode;
import pers.guzx.common.exception.BaseException;
import pers.guzx.user.entity.SysUser;
import pers.guzx.user.serviceImpl.UserDetailServiceImpl;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/7/8 16:29
 * @describe
 */
@Configuration
@Data
public class AuthProvider implements AuthenticationProvider {

    @Resource
    private UserDetailServiceImpl userDetailService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = (String) authentication.getPrincipal();

        UserDetails userDetails = this.userDetailService.loadUserByUsername(username);
        if (Objects.isNull(userDetails)) {
            throw new BaseException(ErrorCode.USER_ACCOUNT_NOT_EXIST);
        }
        AuthToken authToken = new AuthToken(userDetails, userDetails,
                AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
        authToken.setDetails(authentication.getDetails());
        return authToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return AuthToken.class.isAssignableFrom(authentication);
    }
}

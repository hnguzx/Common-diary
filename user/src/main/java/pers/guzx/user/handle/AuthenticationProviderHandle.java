package pers.guzx.user.handle;

import lombok.Data;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/7/7 17:04
 * @describe
 */
@Data
@Configuration
public class AuthenticationProviderHandle implements AuthenticationProvider {

    private UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        AuthenticationTokenHandle authenticationToken = (AuthenticationTokenHandle) authentication;

        UserDetails user = userDetailsService.loadUserByUsername((String) authenticationToken.getPrincipal());
        if (user == null) {
            throw new InternalAuthenticationServiceException("无法获得用户信息");
        }


        AuthenticationTokenHandle authenticationResult = new AuthenticationTokenHandle(user, user.getAuthorities());
        authenticationResult.setDetails(authenticationToken.getDetails());
        return authenticationResult;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return AuthenticationTokenHandle.class.isAssignableFrom(authentication);
    }
}

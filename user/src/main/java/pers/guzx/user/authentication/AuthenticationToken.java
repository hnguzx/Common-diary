package pers.guzx.user.authentication;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.util.Assert;

import java.util.Collection;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/7/8 16:25
 * @describe 自定义认证流程，token令牌。参考：UsernamePasswordAuthenticationToken
 */
@Slf4j
public class AuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    private final Object principal;
    private Object credentials;
    private Boolean isMobileCode;

    public AuthenticationToken(Object principal, Object credentials, Boolean isMobileCode) {
        super(null);
        this.principal = principal;
        this.credentials = credentials;
        this.isMobileCode = isMobileCode;
        setAuthenticated(false);
    }

    public AuthenticationToken(Object principal, Object credentials, Boolean isMobileCode, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        this.isMobileCode = isMobileCode;
        super.setAuthenticated(true);
    }

    public AuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    public Boolean getMobileCode() {
        return this.isMobileCode;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        Assert.isTrue(!isAuthenticated,
                "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        super.setAuthenticated(false);
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        this.credentials = null;
    }
}

package pers.guzx.user.authen;

import lombok.Data;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/7/8 16:21
 * @describe
 */
public class AuthFilter extends AbstractAuthenticationProcessingFilter {

    private static final AntPathRequestMatcher MOBILE_ANT_PATH_REQUEST_MATCHER =
            new AntPathRequestMatcher("/login/mobile", "POST");

    public static final String MOBILE_KEY = "mobile";
    public static final String PASSWORD_KEY = "password";

    private String mobileParameter = MOBILE_KEY;
    private String passwordParameter = PASSWORD_KEY;


    private boolean postOnly = true;

    public AuthFilter() {
        super(MOBILE_ANT_PATH_REQUEST_MATCHER);
    }

    public AuthFilter(AuthenticationManager authenticationManager) {
        super(MOBILE_ANT_PATH_REQUEST_MATCHER, authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (this.postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        String mobile = obtainMobile(request);
        String password = obtainPassword(request);
        AbstractAuthenticationToken authToken = new AuthToken(mobile, password);
        setDetails(request, authToken);
        return this.getAuthenticationManager().authenticate(authToken);
    }


    @Nullable
    protected String obtainMobile(HttpServletRequest request) {
        String mobile = request.getParameter(this.mobileParameter);
        mobile = (mobile != null) ? mobile : "";
        mobile = mobile.trim();
        return mobile;
    }

    @Nullable
    protected String obtainPassword(HttpServletRequest request) {
        String password = request.getParameter(this.passwordParameter);
        password = (password != null) ? password : "";
        return password;
    }

    protected void setDetails(HttpServletRequest request, AbstractAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    public final String getMobileParameter() {
        return this.mobileParameter;
    }

    public final String getPasswordParameter() {
        return this.passwordParameter;
    }

    public void setMobileParameter(String mobileParameter) {
        Assert.hasText(mobileParameter, "Mobile Parameter must not be empty or null");
        this.mobileParameter = mobileParameter;
    }

    public void setPasswordParameter(String passwordParameter) {
        Assert.hasText(passwordParameter, "Password parameter must not be empty or null");
        this.passwordParameter = passwordParameter;
    }
}

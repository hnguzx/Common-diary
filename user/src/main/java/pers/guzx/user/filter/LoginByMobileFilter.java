package pers.guzx.user.filter;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import pers.guzx.user.handle.AuthenticationTokenHandle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/7/7 16:40
 * @describe
 */
public class LoginByMobileFilter extends AbstractAuthenticationProcessingFilter {

    public static final String SPRING_SECURITY_FORM_MOBILE_KEY = "mobile";
    public static final String SPRING_SECURITY_FORM_PASSWORD_KEY = "password";

    private String mobileParameter = SPRING_SECURITY_FORM_MOBILE_KEY;
    private String passwordParameter = SPRING_SECURITY_FORM_PASSWORD_KEY;

    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher("/user/login/mobile",
            "POST");

    public LoginByMobileFilter(){
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER);
        this.setAuthenticationManager(this.authenticationManagerBean());
    }

    protected LoginByMobileFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        if (!httpServletRequest.getMethod().equals("POST")) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + httpServletRequest.getMethod());
        }
        String mobile = obtainMobile(httpServletRequest);
        mobile = (mobile != null) ? mobile : "";

        String password = obtainPassword(httpServletRequest);
        password = (password != null) ? password : "";

        AuthenticationTokenHandle tokenHandle = new AuthenticationTokenHandle(mobile,password);
        setDetails(httpServletRequest, tokenHandle);
        return this.getAuthenticationManager().authenticate(tokenHandle);
    }

    /**
     * 获取手机号码
     *
     * @param request
     * @return
     */
    @Nullable
    protected String obtainMobile(HttpServletRequest request) {
        return request.getParameter(mobileParameter);
    }

    @Nullable
    protected String obtainPassword(HttpServletRequest request) {
        return request.getParameter(this.passwordParameter);
    }

    public void setUsernameParameter(String mobileParameter) {
        Assert.hasText(mobileParameter, "mobile parameter must not be empty or null");
        this.mobileParameter = mobileParameter;
    }

    public void setPasswordParameter(String passwordParameter) {
        Assert.hasText(passwordParameter, "Password parameter must not be empty or null");
        this.passwordParameter = passwordParameter;
    }

    public final String getMobileParameter() {
        return this.mobileParameter;
    }

    public final String getPasswordParameter() {
        return this.passwordParameter;
    }

    protected void setDetails(HttpServletRequest request, AuthenticationTokenHandle tokenHandle) {
        tokenHandle.setDetails(authenticationDetailsSource.buildDetails(request));
    }
}

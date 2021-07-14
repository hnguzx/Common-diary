package pers.guzx.user.authen;

import lombok.Data;
import org.springframework.data.redis.core.RedisTemplate;
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
import pers.guzx.common.code.ErrorCode;
import pers.guzx.common.exception.BaseException;
import pers.guzx.common.util.EmailUtil;
import pers.guzx.common.util.MobileUtil;

import javax.annotation.Resource;
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
            new AntPathRequestMatcher("/login", "POST");

    public static final String USERNAME_KEY = "username";
    public static final String MOBILE_KEY = "mobile";
    public static final String EMAIL_KEY = "email";
    public static final String PASSWORD_KEY = "password";
    public static final String CODE_KEY = "code";

    private String mobileParameter = MOBILE_KEY;
    private String usernameParameter = USERNAME_KEY;
    private String emailParameter = EMAIL_KEY;
    private String passwordParameter = PASSWORD_KEY;
    private String codeParameter = CODE_KEY;

    @Resource
    private RedisTemplate<String, String> redisTemplate;


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
        String username = obtainUsername(request);
        String email = obtainEmail(request);
        AbstractAuthenticationToken authToken = null;
        if (username.length() > 0) {
            authToken = new AuthToken(username, password);
        } else if (mobile.length() > 0 && MobileUtil.isMobile(mobile)) {
            authToken = new AuthToken(mobile, password);
        } else if (email.length() > 0 && EmailUtil.isEmail(email)) {
            authToken = new AuthToken(email, password);
        } else {
            throw new BaseException(ErrorCode.USER_NOT_FOUND);
        }
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
    protected String obtainUsername(HttpServletRequest request) {
        String username = request.getParameter(this.usernameParameter);
        username = (username != null) ? username : "";
        username = username.trim();
        return username;
    }

    @Nullable
    protected String obtainEmail(HttpServletRequest request) {
        String email = request.getParameter(this.emailParameter);
        email = (email != null) ? email : "";
        email = email.trim();
        return email;
    }

    @Nullable
    protected String obtainPassword(HttpServletRequest request) {
        String password = request.getParameter(this.passwordParameter);
        password = (password != null) ? password : "";
        return password;
    }

    @Nullable
    protected String obtainCode(HttpServletRequest request) {
        String code = request.getParameter(this.codeParameter);
        code = (code != null) ? code : "";
        return code;
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

    public final String getUsernameParameter() {
        return this.usernameParameter;
    }

    public final String getEmailParameter() {
        return this.emailParameter;
    }

    public void setUsernameParameter(String usernameParameter) {
        Assert.hasText(usernameParameter, "Username Parameter must not be empty or null");
        this.usernameParameter = usernameParameter;
    }

    public void setEmailParameter(String emailParameter) {
        Assert.hasText(emailParameter, "Email Parameter must not be empty or null");
        this.emailParameter = emailParameter;
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

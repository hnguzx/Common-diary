package pers.guzx.user.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;
import pers.guzx.common.code.ErrorCode;
import pers.guzx.common.exception.BaseException;
import pers.guzx.common.util.EmailUtil;
import pers.guzx.common.util.MobileUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/7/8 16:21
 * @describe 自定义认证过滤器。参考：UsernamePasswordAuthenticationFilter
 */
@Slf4j
public class AuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER =
            new AntPathRequestMatcher("/common/login", "POST");

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

    private boolean postOnly = true;

    public AuthenticationFilter() {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER);
    }

    public AuthenticationFilter(AuthenticationManager authenticationManager) {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER, authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (this.postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        String username = "";
        String mobile = "";
        String email = "";
        String password = "";
        String code = "";

        String contentType = request.getContentType();
        // json格式登录
        if (contentType != null && contentType.equals(MediaType.APPLICATION_JSON_VALUE)) {
            ObjectMapper mapper = new ObjectMapper();
            try (InputStream is = request.getInputStream()) {
                Map<String, String> authenticationBean = mapper.readValue(is, Map.class);
                username = authenticationBean.get("username") == null ? "" : authenticationBean.get("username");
                mobile = authenticationBean.get("mobile") == null ? "" : authenticationBean.get("mobile");
                email = authenticationBean.get("email") == null ? "" : authenticationBean.get("email");
                password = authenticationBean.get("password") == null ? "" : authenticationBean.get("password");
                code = authenticationBean.get("code") == null ? "" : authenticationBean.get("code");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // 表单登录
            username = obtainUsername(request);
            mobile = obtainMobile(request);
            email = obtainEmail(request);
            password = obtainPassword(request);
            code = obtainCode(request);
        }
        Boolean isMobileCode = false;
        if (Boolean.parseBoolean(code)) {
            isMobileCode = true;
        }
        AbstractAuthenticationToken authToken = null;
        if (username.length() > 0) {
            authToken = new AuthenticationToken(username, password, isMobileCode);
        } else if (mobile.length() > 0 && MobileUtil.isMobile(mobile)) {
            authToken = new AuthenticationToken(mobile, password, isMobileCode);
        } else if (email.length() > 0 && EmailUtil.isEmail(email)) {
            authToken = new AuthenticationToken(email, password, isMobileCode);
        } else {
            throw new BaseException(ErrorCode.USER_NOT_FOUND);
        }
        setDetails(request, authToken);
        return this.getAuthenticationManager().authenticate(authToken);
    }

    @Nullable
    protected String obtainUsername(HttpServletRequest request) {
        String username = request.getParameter(this.usernameParameter);
        username = (username != null) ? username : "";
        username = username.trim();
        return username;
    }

    @Nullable
    protected String obtainMobile(HttpServletRequest request) {
        String mobile = request.getParameter(this.mobileParameter);
        mobile = (mobile != null) ? mobile : "";
        mobile = mobile.trim();
        return mobile;
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

    public final String getCodeParameter() {
        return this.codeParameter;
    }

    public void setCodeParameter(String codeParameter) {
        Assert.hasText(codeParameter, "Code Parameter must not be empty or null");
        this.codeParameter = codeParameter;
    }

    public void setUsernameParameter(String usernameParameter) {
        Assert.hasText(usernameParameter, "Username Parameter must not be empty or null");
        this.usernameParameter = usernameParameter;
    }

    public void setMobileParameter(String mobileParameter) {
        Assert.hasText(mobileParameter, "Mobile Parameter must not be empty or null");
        this.mobileParameter = mobileParameter;
    }

    public void setEmailParameter(String emailParameter) {
        Assert.hasText(emailParameter, "Email Parameter must not be empty or null");
        this.emailParameter = emailParameter;
    }

    public void setPasswordParameter(String passwordParameter) {
        Assert.hasText(passwordParameter, "Password parameter must not be empty or null");
        this.passwordParameter = passwordParameter;
    }
}

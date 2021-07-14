package pers.guzx.user.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pers.guzx.user.authen.AuthFilter;
import pers.guzx.user.authen.AuthProvider;
import pers.guzx.user.handle.*;
import pers.guzx.user.serviceImpl.UserDetailServiceImpl;

import javax.annotation.Resource;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/7/6 15:03
 * @describe
 */
@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private UserDetailServiceImpl userDetailService;


    @Resource
    private AuthenticationExceptionHandle authenticationExceptionHandle;
    @Resource
    private AuthenticationAccessDeniedHandler authenticationAccessDeniedHandler;

    @Resource
    private LoginSuccessHandle loginSuccessHandle;
    @Resource
    private LoginFailHandle loginFailHandle;
    @Resource
    private LogoutSuccessHandle logoutSuccessHandle;
    @Resource
    private LoginTimeout loginTimeout;

    public SecurityConfig() {
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors().and().csrf().disable();
        // 登录登出相关
        http.formLogin()
                .successHandler(loginSuccessHandle)
                .failureHandler(loginFailHandle)
                .and().exceptionHandling()
                .accessDeniedHandler(authenticationAccessDeniedHandler)
                .authenticationEntryPoint(authenticationExceptionHandle)
                .and().logout()
                .logoutSuccessHandler(logoutSuccessHandle).deleteCookies()
                .and().sessionManagement().maximumSessions(1).expiredSessionStrategy(loginTimeout);

        //配置无需认证的访问路径
        http.authorizeRequests()
                // 跨域预检请求
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                // 登录URL
                .antMatchers("/login").permitAll()
                // 验证码
                .antMatchers("/user/verificationCode/**").permitAll()
                // 注册
                .antMatchers("/user/registry/**").permitAll()
                // swagger
                .antMatchers("/swagger**/**").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/v2/**").permitAll()
                // 其他所有请求需要身份认证
                .anyRequest().authenticated();

        AuthProvider authProvider = new AuthProvider();
        authProvider.setUserDetailService(userDetailService);
        http.authenticationProvider(authProvider)
                .addFilterAt(authFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                //swagger api json
                "/v2/api-docs",
                //用来获取支持的动作
                "/swagger-resources/configuration/ui",
                //用来获取api-docs的URI
                "/swagger-resources",
                //安全选项
                "/swagger-resources/configuration/security",
                "/swagger-ui.html",
                "/doc.html",
                "/css/**", "/js/**"
        );
    }

    @Bean
    AuthFilter authFilter() throws Exception {
        AuthFilter authFilter = new AuthFilter();
        authFilter.setAuthenticationSuccessHandler(loginSuccessHandle);
        authFilter.setAuthenticationFailureHandler(loginFailHandle);

        authFilter.setFilterProcessesUrl("/login");
        authFilter.setAuthenticationManager(authenticationManagerBean());
        return authFilter;
    }

}

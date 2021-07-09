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
import pers.guzx.user.serviceImpl.SysPermissionServiceImpl;
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

    // 授权处理
    @Resource
    private AuthenticationExceptionHandle authenticationExceptionHandle;
    @Resource
    private AuthenticationAccessDeniedHandler authenticationAccessDeniedHandler;
    // 登录处理
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
        http.formLogin().loginProcessingUrl("/login")
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
                .antMatchers("/login/**").permitAll()
                // swagger
                .antMatchers("/swagger**/**").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/v2/**").permitAll()
                // 其他所有请求需要身份认证
                .anyRequest().authenticated();

        //查询所有权限,动态权限认证
//        List<SysPermission> permissions = permissionService.allPermission();
//        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry authorizeRequests = http
//                .authorizeRequests();
//        permissions.forEach(sysPermission ->
//        {
//            log.info("获取权限为" + sysPermission.getPermCode());
//            //将连接地址对应的权限存入
//            authorizeRequests.antMatchers(sysPermission.getUrl()).hasAnyAuthority(sysPermission.getPermCode());
//        });

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

        authFilter.setFilterProcessesUrl("/login/mobile");
        authFilter.setAuthenticationManager(authenticationManagerBean());
        return authFilter;
    }


}

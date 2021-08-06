package pers.guzx.user.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.vote.AbstractAccessDecisionManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pers.guzx.user.authentication.AuthenticationFilter;
import pers.guzx.user.authentication.AuthenticationProviderImpl;
import pers.guzx.user.authorize.UrlMatchVoter;
import pers.guzx.user.handle.*;
import pers.guzx.user.serviceImpl.UserDetailServiceImpl;

import javax.annotation.Resource;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/7/6 15:03
 * @describe
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

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

    @Resource
    private AuthenticationProviderImpl authProvider;
    @Resource
    private UrlMatchVoter urlMatchVoter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf().disable()
                .authorizeRequests()
                // 登录/退出
                .antMatchers("/common/login").permitAll()
//                .antMatchers("/logout").permitAll()
                // 注册,验证码
                .antMatchers("/user/verificationCode/**", "/user/registry/**", "/user/login/**").permitAll()
                // swagger
                .antMatchers("/swagger**/**").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/v2/**").permitAll()
                .antMatchers("/common/**").access("hasRole('COMMON') or hasRole('ADMIN')or hasRole('USER')")
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user/**").access("hasRole('ADMIN')or hasRole('USER')")
                .antMatchers("/private/**").hasRole("SPECIAL")
                .anyRequest().authenticated()
                // 自定义授权
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O object) {
                        AccessDecisionManager accessDecisionManager = object.getAccessDecisionManager();
                        if (accessDecisionManager instanceof AbstractAccessDecisionManager) {
                            ((AbstractAccessDecisionManager) accessDecisionManager).getDecisionVoters().add(urlMatchVoter);
                        }
                        return object;
                    }
                })
                .and()
                // 自定义认证
                .authenticationProvider(authProvider)
                .addFilterAt(authFilter(), UsernamePasswordAuthenticationFilter.class)
                .formLogin()
                .loginProcessingUrl("/common/login")
                .successHandler(loginSuccessHandle).failureHandler(loginFailHandle)
                .and()
                .exceptionHandling().accessDeniedHandler(authenticationAccessDeniedHandler)
                .authenticationEntryPoint(authenticationExceptionHandle)
                .and()
                .logout()
                .logoutUrl("/logout").logoutSuccessHandler(logoutSuccessHandle).deleteCookies()
                .and()
                .rememberMe().rememberMeParameter("remember").tokenValiditySeconds(300)
                .and()
                .sessionManagement()
                .maximumSessions(1).expiredSessionStrategy(loginTimeout);
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
    AuthenticationFilter authFilter() throws Exception {
        AuthenticationFilter authFilter = new AuthenticationFilter();
        authFilter.setAuthenticationSuccessHandler(loginSuccessHandle);
        authFilter.setAuthenticationFailureHandler(loginFailHandle);
        authFilter.setFilterProcessesUrl("/common/login");
        authFilter.setAuthenticationManager(authenticationManagerBean());
        return authFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

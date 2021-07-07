package pers.guzx.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import pers.guzx.user.filter.LoginByMobileFilter;
import pers.guzx.user.handle.*;
import pers.guzx.user.service.UserService;
import pers.guzx.user.serviceImpl.UserServiceImpl;

import javax.annotation.Resource;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/7/6 15:03
 * @describe
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private UserServiceImpl userService;

    @Resource
    private AuthenticationExceptionHandle authenticationExceptionHandle;
    @Resource
    private LoginSuccessHandle loginSuccessHandle;
    @Resource
    private LoginFailHandle loginFailHandle;
    @Resource
    private LogoutSuccessHandle logoutSuccessHandle;
    @Resource
    private LoginTimeout loginTimeout;

    @Resource
    private AuthenticationProviderHandle providerHandle;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
//                .authenticationProvider(providerHandle)
                .userDetailsService(userService)
                .passwordEncoder(new PasswordEncoderHandle());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors().and().csrf().disable();

        LoginByMobileFilter smsCodeAuthenticationFilter = new LoginByMobileFilter();

//        smsCodeAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        smsCodeAuthenticationFilter.setAuthenticationFailureHandler(loginFailHandle);
//        smsCodeAuthenticationFilter.setAuthenticationSuccessHandler(loginSuccessHandle);
//
//        AuthenticationProviderHandle smsCodeAuthenticationProvider = new AuthenticationProviderHandle();
//        smsCodeAuthenticationProvider.setUserDetailsService(userService);
//
//        http.authenticationProvider(smsCodeAuthenticationProvider)
//                .addFilterAfter(smsCodeAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        http.addFilterBefore(smsCodeAuthenticationFilter,UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/v2/api-docs",
                        "/swagger-resources/configuration/ui",
                        "/swagger-resources",
                        "/swagger-resources/configuration/security",
                        "/swagger-ui.html").permitAll()
                .antMatchers("/user/verifyCode/**",
                        "/user/resetPassword/**",
                        "/user/login/mobile").permitAll()
                .anyRequest().authenticated()
                .and().exceptionHandling().authenticationEntryPoint(authenticationExceptionHandle)
                .and().formLogin().loginProcessingUrl("/user/login/mobile").permitAll()
                .successHandler(loginSuccessHandle)
                .failureHandler(loginFailHandle)
                .and().logout().permitAll()
                .logoutSuccessHandler(logoutSuccessHandle).deleteCookies()
                .and().sessionManagement().maximumSessions(1).expiredSessionStrategy(loginTimeout);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

//    @Bean
//    @Override
//    protected AuthenticationManager authenticationManager() throws Exception {
//        return this.authenticationManager();
//    }
}

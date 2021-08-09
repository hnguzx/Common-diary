package pers.guzx.monitor.config;

import de.codecentric.boot.admin.server.config.AdminServerProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/8/9 10:10
 * @describe
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final String adminContextPath;

    public WebSecurityConfig(AdminServerProperties adminServerProperties) {
        this.adminContextPath = adminServerProperties.getContextPath();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
        http.authorizeRequests()
                //授予对所有静态资产和登录页面的公共访问权限。
                .antMatchers(adminContextPath + "/assets/**").permitAll()
                .antMatchers(adminContextPath + "/login").permitAll()
                //必须对每个其他请求进行身份验证
                .anyRequest().authenticated()
                .and()
                //配置登录和注销
                .formLogin().loginPage(adminContextPath + "/login").successHandler(successHandler).and()
                .logout().logoutUrl(adminContextPath + "/logout").and()
                //启用HTTP-Basic支持。这是Spring Boot Admin Client注册所必需的
                .httpBasic().and()
                .csrf().disable();
    }
}

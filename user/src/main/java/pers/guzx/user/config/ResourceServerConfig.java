package pers.guzx.user.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import javax.annotation.Resource;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/7/23 14:48
 * @describe
 */
/*@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    public static final String RESOURCE_RESOURCES="resources_user";

    @Resource
    private TokenStore tokenStore;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId(RESOURCE_RESOURCES)
                .tokenStore(tokenStore)
                // 无状态模式
                .stateless(true);
    }

    *//**
     * 与spring security类似
     * @param http
     * @throws Exception
     *//*
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/common/login").permitAll()
//                .antMatchers("/admin/**").access("#oauth2.hasScope('admin')")
//                .antMatchers("/private/**").access("#oauth2.hasScope('private')")
                .anyRequest().access("#oauth2.hasScope('common')")
                .and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }
}*/

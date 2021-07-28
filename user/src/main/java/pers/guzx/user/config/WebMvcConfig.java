package pers.guzx.user.config;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/7/22 11:36
 * @describe
 */
//@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 配置可以被跨域的路径，可以具体到请求路径
        registry.addMapping("/**")
                // 允许访问本网站的域名，可以多个
                .allowedOriginPatterns("*")
                // 是否可以将请求的响应暴露给页面
                .allowCredentials(true)
                // 允许进行跨域请求方式，可以多个
                .allowedMethods("GET", "POST", "DELETE", "PATCH")
                // 允许进行跨域请求的header
                .allowedHeaders("*")
                // 前端可以获得的额外响应头详细
                .exposedHeaders("Content-Type")
                // 客户端缓存预检请求的响应时间
                .maxAge(60 * 60 * 24);
    }
}

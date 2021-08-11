package pers.guzx.user.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/8/9 19:07
 * @describe
 */
@Slf4j
public class FeignConfig implements RequestInterceptor {

    @Value("accessToken")
    private String accessToken;

    public FeignConfig() {
    }

    @Override
    public void apply(RequestTemplate requestTemplate) {
        log.info("给请求添加请求头Authentication，token为：" + accessToken);
        requestTemplate.header(HttpHeaders.AUTHORIZATION, accessToken);
    }
}

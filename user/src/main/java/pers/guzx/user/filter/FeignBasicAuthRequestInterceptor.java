package pers.guzx.user.filter;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/8/6 11:43
 * @describe
 */
@Slf4j
@Component
public class FeignBasicAuthRequestInterceptor implements RequestInterceptor {

    public FeignBasicAuthRequestInterceptor() {
    }

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header("Authorization", "Bearer " + System.getProperty("accessToken"));
    }
}

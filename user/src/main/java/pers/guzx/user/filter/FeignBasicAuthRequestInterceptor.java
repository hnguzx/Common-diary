package pers.guzx.user.filter;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/8/6 11:43
 * @describe
 */
public class FeignBasicAuthRequestInterceptor implements RequestInterceptor {

    @Value("accessToken")
    private String accessToken;

    public FeignBasicAuthRequestInterceptor() {
    }

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header("Authorization", "Bearer " + accessToken);
    }
}

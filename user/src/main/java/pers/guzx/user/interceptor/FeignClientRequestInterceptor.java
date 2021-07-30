package pers.guzx.user.interceptor;

import com.google.common.net.HttpHeaders;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.apache.bcel.classfile.ConstantString;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import pers.guzx.user.client.UaaClient;
import pers.guzx.user.entity.JWT;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/7/30 17:44
 * @describe
 */
@Slf4j
@Configuration
public class FeignClientRequestInterceptor implements RequestInterceptor {

    @Resource
    private UaaClient uaaClient;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        HttpServletRequest httpServletRequest = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        if (httpServletRequest != null) {
            //获取头信息
            Map<String, String> headers = getHeaders(httpServletRequest);
            // 传递所有请求头,防止部分丢失
            Set<Map.Entry<String, String>> headerSet = headers.entrySet();

            //RequestTemplate添加请求头
            for (Map.Entry<String, String> e :
                    headerSet) {
                requestTemplate.header(e.getKey(), e.getValue());
            }
            log.debug("FeignRequestInterceptor:{}", requestTemplate.toString());
        }
    }

    /**
     * 获取头信息
     *
     * @param request
     * @return
     */
    private Map<String, String> getHeaders(HttpServletRequest request) {
        Map<String, String> headerMap = new LinkedHashMap<>();
        Enumeration<String> enumeration = request.getHeaderNames();
        if (Objects.nonNull(enumeration)) {
            while (enumeration.hasMoreElements()) {
                String key = enumeration.nextElement();
                String value = request.getHeader(key);
                headerMap.put(key, value);
            }
        }
        String accessToken = getAccessToken();
        headerMap.put(HttpHeaders.AUTHORIZATION, accessToken);
        return headerMap;
    }

    private String getAccessToken(){
        Map<String, String> params = new HashMap<>(4);
        params.put("client_id", "user");
        params.put("client_secret", "123456");
        params.put("grant_type", "client_credentials");
        JWT jwt = uaaClient.oauthToken(params);
        return jwt.getAccess_token();
    }
}

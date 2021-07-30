package pers.guzx.user.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pers.guzx.common.dto.JsonDto;
import pers.guzx.user.entity.JWT;

import java.util.Map;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/7/30 14:03
 * @describe
 */
@FeignClient(name = "uaa")
public interface UaaClient {

    /**
     * 获取授权码
     *
     * @param parameters
     * @return
     */
    @GetMapping("/oauth/authorize")
    String oauthAuthorize(@RequestParam Map<String, String> parameters);

    /**
     * 获取token
     *
     * @param parameters
     * @return
     */
    @PostMapping("/oauth/token")
    JWT oauthToken(@RequestParam Map<String, String> parameters);

    /**
     * 校验token
     *
     * @param tokens
     * @return
     */
    @PostMapping("/check/token")
    JsonDto<Object> checkToken(String[] tokens);
}

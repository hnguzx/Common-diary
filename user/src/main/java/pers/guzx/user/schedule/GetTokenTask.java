package pers.guzx.user.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pers.guzx.user.client.UaaClient;
import pers.guzx.user.entity.JWT;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/8/6 14:08
 * @describe
 */
@Slf4j
@Component
public class GetTokenTask implements ScheduledOfTask {
    @Resource
    private UaaClient uaaClient;

    @Value("system.clientId")
    private String clientId;
    @Value("system.clientSecret")
    private String clientSecret;
    @Value("system.grantType")
    private String grantType;

    @Override
    public void execute() {
        log.info("获取AccessToken");
        Map<String, String> params = new HashMap<>(3);
        params.put("client_id", clientId);
        params.put("client_secret", clientSecret);
        params.put("grant_type", grantType);
        JWT jwt = uaaClient.oauthToken(params);

        System.setProperty("accessToken", jwt.getAccess_token());
    }
}

package pers.guzx.user.schedule;

import lombok.extern.slf4j.Slf4j;
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
public class GetTokenTask {
    @Resource
    private UaaClient uaaClient;

    @Scheduled(cron = "* * */11 * * ?")
    public void getAccessToken() {
        log.info("自动任务执行");
        Map<String, String> params = new HashMap<>(3);
        params.put("client_id", "user");
        params.put("client_secret", "123456");
        params.put("grant_type", "client_credentials");
        JWT jwt = uaaClient.oauthToken(params);

        System.setProperty("accessToken", jwt.getAccess_token());
    }

}

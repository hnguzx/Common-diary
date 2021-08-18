package pers.guzx.demo.web;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.guzx.common.dto.JsonDto;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/8/18 17:41
 * @describe
 */
@RestController
public class ApolloController {

    @GetMapping("/apollo")
    public JsonDto getConfig() {
        Config appConfig = ConfigService.getAppConfig();
        String key = "name";
        String defaultValue = "guzxx";
        String property = appConfig.getProperty(key, defaultValue);
        return JsonDto.retOk(property);
    }
}

package pers.guzx.demo.web;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @Value("${guzx.name}")
    private String name;

    @GetMapping("/apollo/{parameter}")
    public JsonDto getConfig(@PathVariable String parameter) {
        Config appConfig = ConfigService.getAppConfig();
        String defaultValue = "defaultValue";
        String property = appConfig.getProperty(parameter, defaultValue);
        return JsonDto.retOk(property + ":::" + name);
    }
}

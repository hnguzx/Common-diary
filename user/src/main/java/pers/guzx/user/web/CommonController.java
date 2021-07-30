package pers.guzx.user.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pers.guzx.common.dto.JsonDto;

import java.util.Map;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021-07-27 下午 05:17
 * @describe
 */
@RestController
@RequestMapping("/common")
public class CommonController {

    @RequestMapping("/demo")
    public JsonDto demo() {
        return JsonDto.retOk();
    }

    @RequestMapping("/code")
    public JsonDto code(@RequestParam Map<String, String> parameters) {
        String code = parameters.get("code");
        System.out.println("收到code:" + code);
        return JsonDto.retOk();
    }
}

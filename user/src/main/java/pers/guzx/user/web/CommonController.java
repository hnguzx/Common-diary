package pers.guzx.user.web;

import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@RestController
@RequestMapping("/common")
public class CommonController {

    @RequestMapping("/demo")
    public JsonDto demo() {
        log.trace("测试跟踪日志");
        log.debug("测试调试日志");
        log.info("测试普通日志");
        log.warn("测试警告日志");
        log.error("测试错误日志");
        return JsonDto.retOk();
    }

    @RequestMapping("/code")
    public JsonDto code(@RequestParam Map<String, String> parameters) {
        String code = parameters.get("code");
        System.out.println("收到code:" + code);
        return JsonDto.retOk();
    }
}

package pers.guzx.user.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.guzx.common.dto.JsonDto;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021-07-28 下午 05:28
 * @describe
 */
@Slf4j
@RestController
@RequestMapping("/private")
public class PrivateController {

    @RequestMapping("/demo")
    public JsonDto demo() {
        log.trace("测试跟踪日志");
        log.debug("测试调试日志");
        log.info("测试普通日志");
        log.warn("测试警告日志");
        log.error("测试错误日志");
        return JsonDto.retOk();
    }
}

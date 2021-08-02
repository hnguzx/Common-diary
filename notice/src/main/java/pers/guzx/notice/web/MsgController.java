package pers.guzx.notice.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pers.guzx.common.dto.JsonDto;
import pers.guzx.common.validation.Group;
import pers.guzx.notice.entity.SysMessage;
import pers.guzx.notice.serviceImpl.MsgServiceImpl;
import springfox.documentation.spring.web.json.Json;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/7/13 10:05
 * @describe
 */
@Slf4j
@RestController
@RequestMapping("/notice")
public class MsgController {

    @Resource
    private MsgServiceImpl msgService;

    @PostMapping("/mail")
    public JsonDto<Integer> sendMail(@Validated(Group.Add.class) @RequestBody SysMessage message) {
        Integer integer = msgService.sendMail(message);
        return JsonDto.retOk(integer);
    }

    @GetMapping("/registryCode/{email}")
    public JsonDto<Object> verificationCode(@PathVariable String email) {
        msgService.sendCode(email,"registry");
        return JsonDto.retOk();
    }

    @GetMapping("/loginCode/{email}")
    public JsonDto<Object> loginCode(@PathVariable String email) {
        msgService.sendCode(email,"login");
        return JsonDto.retOk();
    }
}

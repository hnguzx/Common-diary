package pers.guzx.notice.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pers.guzx.common.dto.JsonDto;
import pers.guzx.common.validation.Group;
import pers.guzx.notice.entity.SysMessage;
import pers.guzx.notice.serviceImpl.MsgServiceImpl;

import javax.annotation.Resource;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/7/13 10:05
 * @describe
 */
@Slf4j
@RestController
public class MsgController {

    @Resource
    private MsgServiceImpl msgService;

    @PostMapping("/mail")
    public JsonDto<Integer> sendMail(@Validated(Group.Add.class) @RequestBody SysMessage message) {
        Integer integer = msgService.sendMail(message);
        return JsonDto.retOk(integer);
    }

    @GetMapping("/verificationCode/{email}")
    public String verificationCode(@PathVariable String email) {
        String code = msgService.sendCode(email);
        log.info("验证码为：" + code);
        return code;
    }
}
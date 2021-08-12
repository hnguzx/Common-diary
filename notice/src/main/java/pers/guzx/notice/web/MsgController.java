package pers.guzx.notice.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pers.guzx.common.code.ErrorCode;
import pers.guzx.common.dto.JsonDto;
import pers.guzx.common.validation.Group;
import pers.guzx.notice.entity.NoticeType;
import pers.guzx.notice.entity.SysMessage;
import pers.guzx.notice.service.MsgService;
import pers.guzx.notice.serviceImpl.MsgServiceImpl;
import springfox.documentation.spring.web.json.Json;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.util.concurrent.TimeUnit;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/7/13 10:05
 * @describe
 */
@RestController
@RequestMapping("/notice")
public class MsgController {

    @Resource
    private MsgService msgService;

    @PostMapping("/send")
    public JsonDto<Integer> sendMail(@Validated(Group.Add.class) @RequestBody SysMessage message) {
        Integer integer = null;
        try {
            integer = msgService.sendMail(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            return JsonDto.retFail(ErrorCode.EMAIL_SEND_ERROR);
        }
        return JsonDto.retOk(integer);
    }

    @PostMapping("/getNoticeInfo")
    public JsonDto<SysMessage> getNoticeInfo(@RequestBody SysMessage message) {
        SysMessage bySender = msgService.findBySender(message.getSender());
        return JsonDto.retOk(bySender);
    }

    @GetMapping("/registryCode/{email}")
    public JsonDto<Object> verificationCode(@PathVariable String email) {
        try {
            msgService.sendCode(email, NoticeType.REGISTRY.getType());
        } catch (MessagingException e) {
            e.printStackTrace();
            return JsonDto.retFail(ErrorCode.EMAIL_SEND_ERROR);
        }
        return JsonDto.retOk();
    }

    @GetMapping("/loginCode/{email}")
    public JsonDto<Object> loginCode(@PathVariable String email) {
        try {
            msgService.sendCode(email, NoticeType.LOGIN.getType());
        } catch (MessagingException e) {
            e.printStackTrace();
            return JsonDto.retFail(ErrorCode.EMAIL_SEND_ERROR);
        }
        return JsonDto.retOk();
    }
}

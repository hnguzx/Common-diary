package pers.guzx.user.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pers.guzx.common.dto.JsonDto;
import pers.guzx.user.entity.SysMessage;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/7/12 18:24
 * @describe
 */
@FeignClient(name = "notice")
public interface NoticeClient {

    @PostMapping("/notice/mail")
    JsonDto<Integer> sendSimpleEmail(@RequestBody SysMessage message);

    @Async
    @GetMapping("/notice/registryCode/{email}")
    String sendRegistryCode(@PathVariable String email);

    @Async
    @GetMapping("/notice/loginCode/{email}")
    String sendLoginCode(@PathVariable String email);
}

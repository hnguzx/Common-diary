package pers.guzx.user.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pers.guzx.common.dto.JsonDto;
import pers.guzx.user.entity.SysMessage;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/7/13 14:10
 * @describe
 */
@FeignClient(name = "demo")
public interface DemoClient {
    @GetMapping("/demo")
    String demo();
}

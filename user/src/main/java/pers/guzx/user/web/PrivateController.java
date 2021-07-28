package pers.guzx.user.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.guzx.common.dto.JsonDto;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021-07-28 下午 05:28
 * @describe
 */
@RestController
@RequestMapping("/private")
public class PrivateController {

    @RequestMapping("/demo")
    public JsonDto demo() {
        return JsonDto.retOk();
    }
}

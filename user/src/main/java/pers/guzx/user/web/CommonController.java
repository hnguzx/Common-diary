package pers.guzx.user.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.guzx.common.dto.JsonDto;

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
}

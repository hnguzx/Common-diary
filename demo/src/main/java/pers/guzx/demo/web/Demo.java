package pers.guzx.demo.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.guzx.common.dto.JsonDto;

import javax.annotation.Resource;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/7/2 16:55
 * @describe 基础测试controller
 */
@RestController
public class Demo {

    @GetMapping("/demo")
    public String demo() {
        return "success";
    }

    @Resource
    private JsonDto jsonDto;

    @GetMapping("/json")
    public JsonDto jsonDto(){
        return jsonDto;
    }
}

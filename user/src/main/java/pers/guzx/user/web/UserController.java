package pers.guzx.user.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pers.guzx.common.dto.JsonDto;
import pers.guzx.common.validation.Group;
import pers.guzx.user.entity.User;
import pers.guzx.user.serviceImpl.UserServiceImpl;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/7/6 15:08
 * @describe
 */
@Api(tags = "用户系统")
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Resource
    private UserServiceImpl userService;

    @ApiOperation(value = "用户注册")
    @PostMapping("/registry")
    public JsonDto<User> registry(@Validated(Group.Add.class) @RequestBody User user) {
        userService.save(user);
        return JsonDto.retOk(user);
    }

    @GetMapping("/{userId}")
    public JsonDto<User> getUserInfo(@PathVariable("userId") Integer userId) {
        User user = userService.findById(userId);
        return JsonDto.retOk(user);
    }

    @PostMapping("/login/mobile")
    public JsonDto<User> loginByMobile(String mobile, String password, HttpServletRequest request) {
        log.info(mobile + ":" + password);
        return JsonDto.retOk();
    }

}

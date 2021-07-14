package pers.guzx.user.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pers.guzx.common.code.ErrorCode;
import pers.guzx.common.dto.JsonDto;
import pers.guzx.common.validation.Group;
import pers.guzx.user.client.DemoClient;
import pers.guzx.user.client.NoticeClient;
import pers.guzx.user.entity.SysUser;
import pers.guzx.user.serviceImpl.UserServiceImpl;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

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

    @Resource
    private NoticeClient noticeClient;

    @Resource
    private PasswordEncoder passwordEncoder;

    @ApiOperation(value = "用户注册")
    @PostMapping("/registry/{verificationCode}")
    public JsonDto<SysUser> registry(@Validated(Group.Add.class) @RequestBody SysUser sysUser, @PathVariable String verificationCode) {
        String code = redisTemplate.opsForValue().get("registry:" + sysUser.getEmail());
        if (Objects.isNull(code)) {
            return JsonDto.retFail(ErrorCode.VERIFY_NOT_FOUND);
        }
        if (!verificationCode.equals(code)) {
            return JsonDto.retFail(ErrorCode.VERIFY_ERROR);
        }
        String encode = passwordEncoder.encode(sysUser.getPassword());
        sysUser.setPassword(encode);
        userService.save(sysUser);
        return JsonDto.retOk(sysUser);
    }

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @PostMapping("/verificationCode/registry/{email}")
    public JsonDto getVerificationCode(@PathVariable String email) {

        SysUser byEmail = userService.findByEmail(email);
        if (Objects.nonNull(byEmail)) {
            return JsonDto.retFail(ErrorCode.USER_INFO_EXIST);
        }

        String code = noticeClient.sendVerificationCode(email);
        redisTemplate.opsForValue().setIfAbsent("registry:" + email, code, 60, TimeUnit.SECONDS);
        return JsonDto.retOk();
    }

    @Resource
    private DemoClient demoClient;

    @GetMapping("/demo")
    public JsonDto<String> demo() {
        String demo = demoClient.demo();
        return JsonDto.retOk(demo);
    }

}

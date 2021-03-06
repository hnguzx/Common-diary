package pers.guzx.user.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pers.guzx.common.code.ErrorCode;
import pers.guzx.common.dto.JsonDto;
import pers.guzx.common.exception.BaseException;
import pers.guzx.common.validation.Group;
import pers.guzx.user.client.DemoClient;
import pers.guzx.user.entity.SysUserDetails;
import pers.guzx.user.service.AuthorityService;
import pers.guzx.user.service.RoleService;
import pers.guzx.user.service.UserService;

import javax.annotation.Resource;
import javax.jms.JMSException;
import java.util.Objects;

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
@EnableAsync
public class UserController {

    @Resource
    private UserService userService;
    @Resource
    private RoleService roleService;
    @Resource
    private AuthorityService authorityService;

    @ApiOperation(value = "用户注册")
    @PostMapping("/registry/{verificationCode}")
    public JsonDto<SysUserDetails> registry(@Validated(Group.Add.class) @RequestBody SysUserDetails sysUserDetails, @PathVariable String verificationCode) {
        String code = redisTemplate.opsForValue().get("registry:" + sysUserDetails.getEmail());
        if (Objects.isNull(code)) {
            return JsonDto.retFail(ErrorCode.VERIFY_NOT_FOUND);
        }
        if (!verificationCode.equals(code)) {
            return JsonDto.retFail(ErrorCode.VERIFY_ERROR);
        }
        userService.save(sysUserDetails);
        return JsonDto.retOk(sysUserDetails);
    }

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 获取注册验证码
     *
     * @param emailOrMobile
     * @return
     */
    @PostMapping("/verificationCode/registry/{emailOrMobile}")
    public JsonDto<Object> getRegistryCode(@PathVariable String emailOrMobile) {
        try {
            userService.sendRegistryCode(emailOrMobile);
        } catch (JMSException e) {
            e.printStackTrace();
            return JsonDto.retFail(ErrorCode.NOTICE_SEND_FAIL);
        }catch (BaseException e){
            e.printStackTrace();
            return JsonDto.retFail(ErrorCode.USER_INFO_EXIST);
        }
        return JsonDto.retOk();
    }

    /**
     * 获取登录验证码
     *
     * @param emailOrMobile
     * @return
     */
    @PostMapping("/verificationCode/login/{emailOrMobile}")
    public JsonDto<Objects> getLoginCode(@PathVariable String emailOrMobile) {
        try {
            userService.sendLoginCode(emailOrMobile);
        } catch (JMSException e) {
            e.printStackTrace();
            return JsonDto.retFail(ErrorCode.NOTICE_SEND_FAIL);
        }catch (BaseException e){
            e.printStackTrace();
            return JsonDto.retFail(ErrorCode.USER_NOT_FOUND);
        }
        return JsonDto.retOk();
    }

    @Resource
    private DemoClient demoClient;

    @RequestMapping("/demo")
    public JsonDto demo() {
        log.trace("测试跟踪日志");
        log.debug("测试调试日志");
        log.info("测试普通日志");
        log.warn("测试警告日志");
        log.error("测试错误日志");
        return JsonDto.retOk();
    }

}

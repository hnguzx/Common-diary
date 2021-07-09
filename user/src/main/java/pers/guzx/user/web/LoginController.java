package pers.guzx.user.web;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.guzx.common.code.ErrorCode;
import pers.guzx.common.dto.JsonDto;
import pers.guzx.common.exception.BaseException;
import pers.guzx.user.entity.User;
import pers.guzx.user.serviceImpl.UserServiceImpl;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/7/8 11:06
 * @describe
 */
//@RestController
public class LoginController {

    @Resource
    private UserServiceImpl userService;

    @Resource
    private AuthenticationManager authenticationManager;

//    @PostMapping("/login")
//    public JsonDto<User> loginByUsername(@RequestParam String username, @RequestParam String password, HttpServletRequest request) {
//        User byUserName = userService.findByUserName(username);
//        if (byUserName == null) {
//            throw new BaseException(ErrorCode.USER_ACCOUNT_NOT_EXIST);
//        }
//        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//        boolean matches = bCryptPasswordEncoder.matches(password, byUserName.getPassword());
//        if (!matches) {
//            throw new BaseException(ErrorCode.USER_CREDENTIALS_ERROR);
//        }
//        byUserName.setPassword(null);
//        // 系统登录认证
//        return JsonDto.retOk(byUserName);
//    }

    /*@PostMapping("/login/mobile")
    public JsonDto<User> loginByMobile(String mobile, String password, HttpServletRequest request) {
        //security已经做过校验, 这里不再校验验证码
        User ByMobile = userService.findByPhone(mobile);
        if (ByMobile == null) {
            throw new BaseException(ErrorCode.USER_ACCOUNT_NOT_EXIST);
        }
        ByMobile.setPassword(null);
        //进行手动security登陆
        UserDetails userDetails = userService.loadUserByUsername(ByMobile.getUsername());
        AuthenticationTokenHandle tokenHandle = new AuthenticationTokenHandle(mobile, password, userDetails.getAuthorities());
        Authentication authenticate = authenticationManager.authenticate(tokenHandle);
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(authenticate);
        HttpSession session = request.getSession(true);
        //在session中存放security context,方便同一个session中控制用户的其他操作
        session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, context);
        return JsonDto.retOk(ByMobile);
    }*/

    public JsonDto<User> loginByEmail() {
        return JsonDto.retOk();
    }
}

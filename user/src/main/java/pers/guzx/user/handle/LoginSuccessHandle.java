package pers.guzx.user.handle;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import pers.guzx.common.dto.JsonDto;
import pers.guzx.user.client.UaaClient;
import pers.guzx.user.convert.UserConvert;
import pers.guzx.user.dto.UserDto;
import pers.guzx.user.entity.JWT;
import pers.guzx.user.entity.SysUserDetails;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/7/7 16:18
 * @describe
 */
@Slf4j
@Component
public class LoginSuccessHandle implements AuthenticationSuccessHandler {

    @Resource
    private UserDetailsService userAuthDetailsService;
    @Resource
    private UserConvert userConvert;
    @Resource
    private UaaClient uaaClient;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        log.info("login success!");
        this.onAuthenticationSuccess(request, response, authentication);
        chain.doFilter(request, response);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        //更新用户表上次登录时间、更新人、更新时间等字段
        SysUserDetails user = (SysUserDetails) authentication.getPrincipal();

        /*Map<String, String> params = new HashMap<>(4);
        params.put("client_id", "user");
        params.put("client_secret", "123456");
        params.put("grant_type", "client_credentials");
        JWT jwt = uaaClient.oauthToken(params);*/

        SysUserDetails userDetails = (SysUserDetails) userAuthDetailsService.loadUserByUsername(user.getUsername());

        UserDto userDto = userConvert.convert(userDetails);
//        userDto.setToken(jwt.getAccess_token());
        //此处还可以进行一些处理，比如登录成功之后可能需要返回给前台当前用户有哪些菜单权限，
        //进而前台动态的控制菜单的显示等，具体根据自己的业务需求进行扩展

        //处理编码方式，防止中文乱码的情况
        httpServletResponse.setContentType("text/json;charset=utf-8");
        //塞到HttpServletResponse中返回给前台
        httpServletResponse.getWriter().write(JSON.toJSONString(JsonDto.retOk(userDto)));
    }
}

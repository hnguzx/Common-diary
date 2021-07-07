package pers.guzx.user.handle;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import pers.guzx.common.dto.JsonDto;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/7/7 16:26
 * @describe
 */
@Slf4j
@Component
public class LogoutSuccessHandle implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException {
        Map<String, String[]> parameterMap = httpServletRequest.getParameterMap();
        String username = parameterMap.get("username")[0];
//        redisTemplate.opsForHash().delete("loggedUser", username);
        log.trace("用户退出系统：" + username);
        httpServletResponse.setContentType("text/json;charset=utf-8");
        httpServletResponse.getWriter().write(JSON.toJSONString(JsonDto.retOk()));
    }
}

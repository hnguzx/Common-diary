package pers.guzx.user.handle;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.stereotype.Component;
import pers.guzx.common.code.ErrorCode;
import pers.guzx.common.dto.JsonDto;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/7/7 16:36
 * @describe
 */
@Slf4j
@Component
public class LoginTimeout implements SessionInformationExpiredStrategy {
    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent sessionInformationExpiredEvent) throws IOException {
        HttpServletResponse httpServletResponse = sessionInformationExpiredEvent.getResponse();
        httpServletResponse.setContentType("text/json;charset=utf-8");
        httpServletResponse.getWriter().write(JSON.toJSONString(JsonDto.retOk(ErrorCode.USER_ACCOUNT_USE_BY_OTHERS)));
    }
}

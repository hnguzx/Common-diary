package pers.guzx.common.handle;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import pers.guzx.common.code.ErrorCode;
import pers.guzx.common.dto.JsonDto;
import pers.guzx.common.exception.BaseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/7/7 9:55
 * @describe
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler implements WebMvcConfigurer {

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
        resolvers.add((request, response, handler, e) -> {
            JsonDto<ErrorCode> result;
            if (e instanceof BaseException) {
                result = JsonDto.retFail(((BaseException) e).getErrorCode());
            } else if (e instanceof MethodArgumentNotValidException) {
                StringBuilder errorMsg = new StringBuilder();
                List<ObjectError> allErrors = ((MethodArgumentNotValidException) e)
                        .getBindingResult().getAllErrors();
                for (ObjectError error : allErrors) {
                    errorMsg.append(error.getDefaultMessage());
                    errorMsg.append(";");
                }
                result = setResult(ErrorCode.DATA_VALIDATE.getCode(), errorMsg.toString());
            } else if (e instanceof NoHandlerFoundException) {
                result = setResult(ErrorCode.NOT_FOUND.getCode(),
                        "Interface [" + request.getRequestURI() + "] NOT FOUND");
            } else if (e instanceof ServletException) {
                result = setResult(ErrorCode.SERVICE_UNAVAILABLE.getCode(), e.getMessage());
            } else {
                String message;
                if (handler instanceof HandlerMethod) {
                    HandlerMethod handlerMethod = (HandlerMethod) handler;
                    message = String.format("Interface [%s] Exception,Method：%s.%s,Exception Details：%s",
                            request.getRequestURI(),
                            handlerMethod.getBean().getClass().getName(),
                            handlerMethod.getMethod().getName(),
                            e.getMessage());
                } else {
                    message = e.getMessage();
                }
                result = setResult(ErrorCode.INTERNAL_SERVER_ERROR.getCode(), message);
            }
            log.error(result.getMessage());
            responseResult(response, result);
            return new ModelAndView();
        });
    }

    private JsonDto<ErrorCode> setResult(Integer code, String message) {
        JsonDto<ErrorCode> jsonDto = new JsonDto<>();
        jsonDto.setCode(code);
        jsonDto.setMessage(message);
        return jsonDto;
    }

    private void responseResult(HttpServletResponse response, JsonDto<ErrorCode> result) {
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-type", "application/json;charset=UTF-8");
        response.setStatus(200);
        try {
            response.getWriter().write(JSON.toJSONString(result));
        } catch (IOException ex) {
            log.error(ex.getMessage());
        }
    }
}

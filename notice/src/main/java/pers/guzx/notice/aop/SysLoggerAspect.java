package pers.guzx.notice.aop;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pers.guzx.common.annotation.SysLogger;
import pers.guzx.common.entity.SysLog;
import pers.guzx.common.util.HttpUtils;
import pers.guzx.notice.service.LoggerService;
import pers.guzx.notice.util.UserUtil;

import javax.jms.JMSException;
import java.lang.reflect.Method;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/8/12 17:43
 * @describe
 */
@Aspect
@Component
public class SysLoggerAspect {
    @Autowired
    private LoggerService loggerService;

    @Pointcut("@annotation(pers.guzx.common.annotation.SysLogger)")
    public void loggerPointCut() {

    }

    @Before("loggerPointCut()")
    public void saveSysLog(JoinPoint joinPoint) throws JMSException {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        SysLog sysLog = new SysLog();
        SysLogger sysLogger = method.getAnnotation(SysLogger.class);
        if (sysLogger != null) {
            //注解上的描述
            sysLog.setUserOperation(sysLogger.value());
        }
        //请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        sysLog.setRequestMethod(className + "." + methodName + "()");
        //请求的参数
        Object[] args = joinPoint.getArgs();
        String params = "";
        for (Object o : args) {
            params += JSON.toJSONString(o);
        }
        if (!StringUtils.isEmpty(params)) {
            sysLog.setRequestParams(params);
        }
        //设置IP地址
        sysLog.setRequestIp(HttpUtils.getIpAddress());
        //用户名
        String username = UserUtil.getCurrentPrinciple();
        if (!StringUtils.isEmpty(username)) {
            sysLog.setUsername(username);
        }
        //保存系统日志
        loggerService.log(sysLog);
    }

}


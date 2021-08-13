package pers.guzx.log.handle;

import com.alibaba.fastjson.JSONObject;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import pers.guzx.log.entity.SysLog;
import pers.guzx.log.service.SysLogService;

import javax.annotation.Resource;
import javax.jms.MapMessage;
import javax.jms.Message;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/8/12 17:27
 * @describe
 */
@Component
public class ActiveMQHandle {

    @Resource
    private SysLogService logService;

    @JmsListener(destination = "${myqueue.log}")//监听哪个队列
    public void send(Message message) {
        //将message对象转为MapMessage
        MapMessage mapMessage = (MapMessage) message;
        try {
            String log = mapMessage.getString("sys-log");
            JSONObject jsonObject = JSONObject.parseObject(log);
            SysLog sysLog = jsonObject.toJavaObject(SysLog.class);
            logService.save(sysLog);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

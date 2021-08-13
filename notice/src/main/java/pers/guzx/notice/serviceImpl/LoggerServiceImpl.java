package pers.guzx.notice.serviceImpl;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQMapMessage;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;
import pers.guzx.common.entity.SysLog;
import pers.guzx.notice.service.LoggerService;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.Topic;


/**
 * @author Administrator
 */
@Slf4j
@Service
public class LoggerServiceImpl implements LoggerService {

    @Resource
    private JmsMessagingTemplate messagingTemplate;
    @Resource
    private Queue logQueue;
    @Resource
    private Topic logTopic;

    @Override
    public void log(SysLog sysLog) throws JMSException {
        ActiveMQMapMessage mapMessage = new ActiveMQMapMessage();
        mapMessage.setString("sys-log", JSON.toJSONString(sysLog));
        messagingTemplate.convertAndSend(logQueue, mapMessage);
    }
}

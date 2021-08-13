package pers.guzx.notice.handle;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import pers.guzx.notice.service.MsgService;

import javax.annotation.Resource;
import javax.jms.MapMessage;
import javax.jms.Message;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/8/2 16:46
 * @describe
 */
@Component
public class MQHandle {

    @Resource
    private MsgService msgService;

    @JmsListener(destination = "${myqueue.notice}")//监听哪个队列
    public void send(Message message) {
        //将message对象转为MapMessage
        MapMessage mapMessage = (MapMessage) message;
        try {
            String emailOrMobile = mapMessage.getString("emailOrMobile");
            String noticeType = mapMessage.getString("noticeType");
            msgService.sendCode(emailOrMobile, noticeType);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

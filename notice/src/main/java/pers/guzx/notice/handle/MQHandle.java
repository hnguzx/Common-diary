package pers.guzx.notice.handle;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

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

    @JmsListener(destination = "${queue}")//监听哪个队列
    public void send(Message message) {
        //将message对象转为MapMessage
        MapMessage mapMessage = (MapMessage) message;
        try {
            String emailOrMobile = mapMessage.getString("emailOrMobile");
            String noticeType = mapMessage.getString("noticeType");
            //打印队列中的消息查看
            System.out.println(emailOrMobile);
            System.out.println(noticeType);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

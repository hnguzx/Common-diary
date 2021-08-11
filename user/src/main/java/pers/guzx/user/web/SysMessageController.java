package pers.guzx.user.web;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pers.guzx.user.entity.SysMessage;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.TextMessage;
import java.security.Principal;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/8/3 15:41
 * @describe
 */
@RestController
public class SysMessageController {

    @Resource
    private SimpMessagingTemplate simpMessagingTemplate;

    /**
     * 广播模式
     *
     * @param str
     * @return
     */
    @MessageMapping("/all")
    public void sendToAll(String str) {
        simpMessagingTemplate.convertAndSend("/topic/all", "所有人的消息");
    }

    @MessageMapping("/group/{groupId}")
    public void sendToGroup(@DestinationVariable String groupId) {
        simpMessagingTemplate.convertAndSend("/topic/" + groupId, "群消息");
    }

    @MessageMapping("/test")
//    @SendTo("/topic/test")
    public String sendToTest(String str) {
        // 有return的消息默认转发到/topic/xxx下
        return "test";
    }

    /*@JmsListener(destination = "${myqueue}")
    public void sendMQ(TextMessage message) throws JMSException {
        //将message对象转为MapMessage
        String text = message.getText();
        System.out.println(text);
        simpMessagingTemplate.convertAndSend("/topic/all",text);
    }*/

    /**
     * 用户模式
     */
    @MessageMapping("/user")
    public void sendToUser(@RequestBody SysMessage message, Principal principal) {
        System.out.println(message.getContent());
        simpMessagingTemplate.convertAndSendToUser(message.getReceiver(), "/queue/one", "单独发送的消息");
    }

    /**
     * 订阅模式
     *
     * @return
     */
    @SubscribeMapping("/connect")
    public String connect() {
        System.out.println("用户连接成功");
        return "用户连接成功";
    }

    @SubscribeMapping({"/disConnect"})
    public String closeConnect() {
        System.out.println("用户断开连接");
        return "用户断开连接";
    }
}

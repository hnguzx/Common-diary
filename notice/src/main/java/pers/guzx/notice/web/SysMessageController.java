package pers.guzx.notice.web;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
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
        simpMessagingTemplate.convertAndSend("/topic", "所有人的消息");
    }

    /**
     * 用户模式
     */
    @MessageMapping("/user")
    public void sendToUser() {
        simpMessagingTemplate.convertAndSendToUser("admin", "/queue/one", "单独发送的消息");
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

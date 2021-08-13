package pers.guzx.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;


/**
 * @author Guzx
 * @version 1.0
 * @date 2021/8/3 15:30
 * @describe
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry
                .addEndpoint("/ws")
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //客户端订阅消息的请求前缀，topic一般用于广播推送，queue用于点对点推送
        registry.enableSimpleBroker("/topic", "/queue");
        //客户端发送消息的请求前缀
        registry.setApplicationDestinationPrefixes("/notice");
        //服务端通知客户端的前缀，可以不设置，默认为user
        registry.setUserDestinationPrefix("/user/");
    }

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}

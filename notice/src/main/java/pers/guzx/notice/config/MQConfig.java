package pers.guzx.notice.config;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.Queue;
import javax.jms.Topic;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/8/2 16:14
 * @describe
 */
@Configuration
public class MQConfig {

    @Bean
    public Queue queue(){
        return new ActiveMQQueue("notice-queue");
    }

    @Bean
    public Topic topic(){
        return new ActiveMQTopic("test-topic");
    }
}

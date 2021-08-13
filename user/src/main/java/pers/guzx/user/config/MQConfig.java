package pers.guzx.user.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.jms.Queue;
import javax.jms.Topic;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/8/2 16:14
 * @describe
 */
@Configuration
@EnableJms
public class MQConfig {

    @Value("${myqueue.notice}")
    private String noticeQueue;
    @Value("${myqueue.log}")
    private String logQueue;
    @Value("${mytopic.notice}")
    private String noticeTopic;
    @Value("${mytopic.log}")
    private String logTopic;


    @Bean
    public Queue noticeQueue() {
        return new ActiveMQQueue(noticeQueue);
    }

    @Bean
    public Queue logQueue() {
        return new ActiveMQQueue(logQueue);
    }

    @Bean
    public Topic noticeTopic() {
        return new ActiveMQTopic(noticeTopic);
    }

    @Bean
    public Topic logTopic() {
        return new ActiveMQTopic(logTopic);
    }

    /**
     * topic模式的ListenerContainer
     *
     * @return
     */
    @Bean
    public JmsListenerContainerFactory<?> topicListenerFactory() {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        ActiveMQConnectionFactory connectionFactor = new ActiveMQConnectionFactory();
        factory.setPubSubDomain(true);
        factory.setConnectionFactory(connectionFactor);
        return factory;
    }

    /**
     * queue模式的ListenerContainer
     *
     * @return
     */
    @Bean
    public JmsListenerContainerFactory<?> queueListenerFactory() {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        ActiveMQConnectionFactory connectionFactor = new ActiveMQConnectionFactory();
        factory.setPubSubDomain(false);
        factory.setConnectionFactory(connectionFactor);
        return factory;
    }
}

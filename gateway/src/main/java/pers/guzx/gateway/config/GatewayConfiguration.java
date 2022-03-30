package pers.guzx.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pers.guzx.gateway.filter.CommonFilter;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/5/15 17:47
 * @describe
 */
@Configuration
public class GatewayConfiguration {

    @Bean
    public CommonFilter commonFilter(){
        return new CommonFilter();
    }
}
